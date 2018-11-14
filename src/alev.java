
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ElHa
 */
public class alev {

    Map Inisiate_population(int jum, int gen) {
        Map<Integer, List> initial_pop = new HashMap();
        int i = 0;
        int max = gen + 1, min = i + 1;
        for (int j = 0; j < jum; j++) {
            List<Integer> kromosom = new ArrayList();
            do {
                int a;
                for (int k = 0; k < gen; k++) {
                    do {
                        a = (int) (Math.random() * (max - min) + min);
                    } while (kromosom.indexOf(a) != -1);
                    kromosom.add(a);
                }
            } while (initial_pop.containsValue(kromosom));
            initial_pop.put(j, kromosom);
        }
        return initial_pop;
    }

    Map crosover_all(Map initial_pop, double cr) {
        Map data = new HashMap();
        data.putAll(initial_pop);
        //Map index_terpakai = new HashMap();
        Map<Integer, List> temp = new HashMap();
        int offspring = (int) (cr * initial_pop.size());
        //System.out.println("jumlah off crossover " + offspring);
        for (int k = 0; k < offspring; k++) {
            int index[] = new int[2];
            index[0] = (int) (Math.random() * (data.size() - 0) + 0);
            do {
                index[1] = (int) (Math.random() * (data.size() - 0) + 0);
            } while (index[1] == index[0]);
            temp.put(k, crossover((List) data.get(index[0]), (List) data.get(index[1])));
            //temp_hasil1.putAll(temp_hasil);
//            System.out.println("-----------------");
//            System.out.println(temp_hasil1);
        }
        // System.out.println(temp_hasil1);
        return temp;
    }

    boolean cek_cros(List data, int data1) {
        if (data.contains(data1)) {
            return true;
        }
        return false;
    }

    List crossover(List data1, List data2) {
        List dat1=new ArrayList(data1), dat2=new ArrayList(data2);
        List off= new ArrayList();
        int jumambil=(int)(Math.random() * ((dat1.size()-2) - 2) + 2);
        //System.out.println("jumlah terambil"+jumambil);
        int index_ambil = (int)(Math.random() * ((dat1.size()-jumambil) - 0) + 0);
        //System.out.println("index terambil "+index_ambil);
        List data_terambil = dat1.subList(index_ambil, index_ambil+jumambil);
//        System.out.println("data1"+data1);
//        System.out.println("data2"+data2);
        for (int i = 0; i < data_terambil.size(); i++) {
            dat2.remove(data_terambil.get(i));
        }
        //System.out.println(dat2);
        //System.out.println(data_terambil);
        int tengah= dat2.size()/2;
        for (int i = 0; i < tengah; i++) {
            off.add(dat2.get(i));
        }
        for (int i = 0; i < data_terambil.size(); i++) {
            off.add(data_terambil.get(i));
        }
        for (int i = 0; i < dat2.size()-tengah; i++) {
            off.add(dat2.get(tengah+i));
        }
        //System.out.println("off "+off);
        return off;
    }

    Map mutasi(Map initial_pop, double mr) {
        Map data = new HashMap(initial_pop);
        
        Map<Integer, List> off_mutasi = new HashMap();
        int offspring = (int) (mr * data.size());
        List<Integer> individu_terpilih = new ArrayList();
        int temp_index;
        for (int i = 0; i < offspring; i++) {
            do {
                temp_index = (int) (Math.random() * ((data.size() - 1) - 0) + 0);
            } while (individu_terpilih.indexOf(temp_index) != -1);
            individu_terpilih.add(temp_index);
        }
        //System.out.println("jum off mutasi = " + offspring);
        //System.out.println("index indivisu terpilih "+individu_terpilih);
        for (int i = 0; i < individu_terpilih.size(); i++) {
            List a = (List) data.get(1);
            int index[] = new int[2];
            index[0] = (int) (Math.random() * ((a.size() - 1) - 0) + 0);
            do {
                index[1] = (int) (Math.random() * ((a.size() - 1) - 0) + 0);
            } while (index[1] == index[0]);
            List temp = proses_mutasi((List) data.get(individu_terpilih.get(i)), index[0], index[1]);
            off_mutasi.put(i, temp);
        }
        return off_mutasi;
    }

    List proses_mutasi(List data, int index, int index1) {
        //System.out.println("--------");
        //System.out.println("index "+index+" dengan "+index1);
        List hasil = new ArrayList(data);
        int temp = (int) hasil.get(index);
        int temp1 = (int) hasil.get(index1);
        //System.out.println("before "+hasil);
        hasil.set(index, temp1);
        hasil.set(index1, temp);
        //System.out.println("after "+hasil);
        return hasil;
    }

    List get_fitnes(Map initial_pop, Map off_cros, Map off_mut, Map data, int panjang_bahan) {
        List<Double> fitnes = new ArrayList();
        for (int i = 0; i < initial_pop.size(); i++) {
            double sisa = (hitung_bahan((List) initial_pop.get(i), data, panjang_bahan));
            fitnes.add(sisa);
        }
        for (int i = 0; i < off_cros.size(); i++) {
            double sisa = (hitung_bahan((List) off_cros.get(i), data, panjang_bahan));
            fitnes.add(sisa);
        }
        for (int i = 0; i < off_mut.size(); i++) {
            double sisa = (hitung_bahan((List) off_mut.get(i), data, panjang_bahan));
            fitnes.add(sisa);
        }
        return fitnes;
    }

    Map seleksi(Map initial_pop, Map off_cros, Map off_mut, int[] index_urutan, int popsize) {
        Map populasi = new HashMap();
        Map new_populasi = new HashMap();
        populasi.putAll(initial_pop);
        for (int i = 0; i < off_cros.size(); i++) {
            populasi.put(initial_pop.size() + i, off_cros.get(i));
        }
        for (int i = 0; i < off_mut.size(); i++) {
            populasi.put(populasi.size(), off_mut.get(i));
        }
        //System.out.println(populasi);
        for (int i = 0; i < popsize; i++) {
            new_populasi.put(i, populasi.get(index_urutan[i]));
        }
        return new_populasi;

    }

    int hitung_bahan(List data, Map panjang, int panjang_bahan) {
        int tot = 0, kebutuhan = 1;
        int bahan_tersedia = panjang_bahan;
        for (int i = 0; i < data.size(); i++) {
            if ((bahan_tersedia - (int) panjang.get(data.get(i))) >= 0) {
                bahan_tersedia -= (int) panjang.get(data.get(i));
            } else {
                kebutuhan++;
                tot += bahan_tersedia;
                bahan_tersedia = panjang_bahan;
                bahan_tersedia -= (int) panjang.get(data.get(i));
            }
            if (i == data.size() - 1) {
                if (bahan_tersedia > 0) {
                    tot += bahan_tersedia;
                }
            }
        }
        return tot;
    }

    int[] sort(List fitnes) {
        List temp = fitnes;
        List temp2 = new ArrayList(temp);
        Collections.sort(temp);
        int[] indexes = new int[temp.size()];
        for (int n = 0; n < temp.size(); n++) {
            indexes[n] = temp2.indexOf(temp.get(n));
            temp2.set(indexes[n], -1);
        }
        //System.out.println(Arrays.toString(indexes));
        return indexes;
    }

    public static void main(String[] args) {
        int panjang_bahan =500;
        model model_a= new model(50,24,30,10);
        model model_b= new model(10,60,25,80);
        model_a.set_jum(4, 2, 1, 4);//200, 48,30,10=288
        model_b.set_jum(2, 3, 4, 1);//20,180,100,80=380=668
        Map data = new HashMap<Integer, Integer>();
        data.put(1, 20);
        data.put(2, 34);
        data.put(3, 35);
        data.put(4, 45);
        data.put(5, 75);
        data.put(6, 15);
        data.put(7, 10);
        data.put(8, 63);
        data.put(9, 24);
        
        alev baru = new alev();
        Map coba = baru.Inisiate_population(2, data.size());
        System.out.println(coba);
        System.out.println("=======");
        Map offspring1 = baru.crosover_all(coba, 0.6);
//        System.out.println(offspring1);
//        System.out.println(coba);
//        System.out.println("============");
//        System.out.println("mutasi");
//        System.out.println("============");
        Map offspring2 = baru.mutasi(coba, 0.6);
        System.out.println(offspring2);
        List fitnes = baru.get_fitnes(coba, offspring1, offspring2, data, 150);
        System.out.println(fitnes);
        int index[] = baru.sort(fitnes);
        List a= (List)coba.get(0);
        for (int i = 0; i < a.size(); i++) {
            System.out.print(a.get(i)+" ");
        }
        System.out.println("");
        for (int i = 0; i < a.size(); i++) {
            System.out.print(data.get(a.get(i))+" ");
        }
        System.out.println("");
        System.out.println(baru.hitung_bahan((List)coba.get(0), data, 150));
        Map populasi = baru.seleksi(coba, offspring1, offspring2, index, 4);
        //System.out.println(populasi);

    }
}
