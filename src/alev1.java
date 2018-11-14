
import java.util.AbstractList;
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
public class alev1 {

    Map Inisiate_population(int jum, int gen, Map data, List kebutuhan) {
        List temp_data = new ArrayList();
        for (int i = 0; i < data.size(); i++) {
            temp_data.add(data.get(i + 1));
            System.out.print((i + 1) + " ");
        }
        System.out.println("");
        Map<Integer, List> initial_pop = new HashMap();
        int panjang_kromosom = 0;
        for (int i = 0; i < kebutuhan.size(); i++) {
            panjang_kromosom += (int) kebutuhan.get(i);
            System.out.print(kebutuhan.get(i) + " ");
        }
        System.out.println("");
        int i = 0;
        int max = gen + 1, min = i + 1;
        for (int j = 0; j < jum; j++) {
            List<Integer> kromosom = new ArrayList();
            do {
                int a;
                for (int k = 0; k < panjang_kromosom; k++) {
                    int index;
                    do {
                        a = (int) (Math.random() * (max - min) + min);
                        //System.out.println(a);
                        //System.out.println("dasd"+temp_data.get(a-1));
                        index = temp_data.indexOf(temp_data.get(a - 1));
                        //System.out.println("index"+index);
                    } while (Collections.frequency(kromosom, a) == (int) kebutuhan.get(index));
                    kromosom.add(a);
                }
            } while (initial_pop.containsValue(kromosom));
            System.out.println(kromosom.size());
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
        }
        return temp;
    }

    List crossover(List data1, List data2) {
        List dat1 = new ArrayList(data1), dat2 = new ArrayList(data2);
        List off = new ArrayList();
        int jumambil = (int) (Math.random() * ((dat1.size() - 2) - 2) + 2);
        //System.out.println("jumlah terambil "+jumambil);
        int index_ambil = (int) (Math.random() * ((dat1.size() - jumambil) - 0) + 0);
        //System.out.println("index terambil "+index_ambil);
        List data_terambil = dat1.subList(index_ambil, index_ambil + jumambil);
//        System.out.println("data1"+data1);
//        System.out.println("data2"+data2);
        for (int i = 0; i < data_terambil.size(); i++) {
            dat2.remove(data_terambil.get(i));
        }
        //System.out.println(dat2);
        //System.out.println(data_terambil);
        int tengah = dat2.size() / 2;
        for (int i = 0; i < tengah; i++) {
            off.add(dat2.get(i));
        }
        for (int i = 0; i < data_terambil.size(); i++) {
            off.add(data_terambil.get(i));
        }
        for (int i = 0; i < dat2.size() - tengah; i++) {
            off.add(dat2.get(tengah + i));
        }
        //System.out.println("off "+off);
        return off;
    }

    Map scramble_mutasi(Map initial_pop, double mr) {
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
            int jum_gen_mut=(int) (Math.random() * ((a.size() - 1) - 1) + 1);
            System.out.println(jum_gen_mut);
            int index = (int) (Math.random() * ((a.size() - jum_gen_mut) - 0) + 0);
            System.out.println("index "+index);
            List temp = proses_scramble(index, jum_gen_mut, (List)data.get(individu_terpilih.get(i)));
            off_mutasi.put(i, temp);
        }
        return off_mutasi;
    }
    List proses_scramble(int index_mulai, int jum_gen,List data){
        List data1=new ArrayList(data);
        List bag1=data1.subList(0, index_mulai);
        List bag2=data1.subList(index_mulai+jum_gen, data1.size());
        List temp_terandom=data1.subList(index_mulai, index_mulai+jum_gen);       
        List terandom= new ArrayList(temp_terandom);
        Collections.shuffle(terandom);      
        List hasil=new ArrayList(bag1);
        hasil.addAll(terandom);
        hasil.addAll(bag2);      
        return hasil;
    }

    double hitung_fitnes(List sisa, int bahan) {
        double fitnes;
        int m = sisa.size();
        int L = bahan;
        double a = 1.0/(m + 1);
        //System.out.println("a "+a);
        double b = 0, c = 0;
        for (int i = 0; i < sisa.size(); i++) {
            double sisa1 = (int)sisa.get(i);
            double temp1= sisa1/L;
            double temp = Math.sqrt(temp1);
            b += temp;
        }
        //System.out.println("b "+b);
        for (int i = 0; i < sisa.size(); i++) {
            int v;
            if ((int) sisa.get(i) > 0) {
                v = 1;
            } else {
                v = 0;
            }
            double temp = (double)v/m;
            c+=temp;
        }
        //System.out.println("c "+c);
        fitnes = a*(b+c);
        return fitnes;
    }

    List get_fitnes(Map initial_pop, Map off_cros, Map off_mut, Map data, int panjang_bahan) {
        List<Double> fitnes = new ArrayList();
        for (int i = 0; i < initial_pop.size(); i++) {
            List sisa = (hitung_bahan((List) initial_pop.get(i), data, panjang_bahan));
            double temp_fitnes=hitung_fitnes(sisa, panjang_bahan);
            fitnes.add(temp_fitnes);
        }
        for (int i = 0; i < off_cros.size(); i++) {
            List sisa = (hitung_bahan((List) off_cros.get(i), data, panjang_bahan));
            double temp_fitnes=hitung_fitnes(sisa, panjang_bahan);
            fitnes.add(temp_fitnes);
        }
        for (int i = 0; i < off_mut.size(); i++) {
            List sisa = (hitung_bahan((List) off_mut.get(i), data, panjang_bahan));
            double temp_fitnes=hitung_fitnes(sisa, panjang_bahan);
            fitnes.add(temp_fitnes);
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

    List hitung_bahan(List data, Map panjang, int panjang_bahan) {
        int tot = 0;
        List sisa = new ArrayList();
        int bahan_tersedia = panjang_bahan;
        for (int i = 0; i < data.size(); i++) {
            if ((bahan_tersedia - (int) panjang.get(data.get(i))) >= 0) {
                bahan_tersedia -= (int) panjang.get(data.get(i));
            } else {
                sisa.add(bahan_tersedia);
                bahan_tersedia = panjang_bahan;
                bahan_tersedia -= (int) panjang.get(data.get(i));
            }
            if (i == data.size() - 1) {
                if (bahan_tersedia > 0) {
                    sisa.add(bahan_tersedia);
                }
            }
        }
        return sisa;
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
//

    public static void main(String[] args) {
        int panjang_bahan = 14;
        List kebutuhan = new ArrayList();
        kebutuhan.add(5);
        kebutuhan.add(2);
        kebutuhan.add(1);
        kebutuhan.add(2);
        kebutuhan.add(4);
        kebutuhan.add(5);
        kebutuhan.add(1);
        kebutuhan.add(3);
        Map data = new HashMap();
        data.put(1, 3);
        data.put(2, 4);
        data.put(3, 5);
        data.put(4, 6);
        data.put(5, 7);
        data.put(6, 8);
        data.put(7, 9);
        data.put(8, 10);

        alev1 baru = new alev1();
        Map coba = baru.Inisiate_population(2, data.size(), data, kebutuhan);
        System.out.println(coba);
        System.out.println("=======");
        Map offspring1 = baru.crosover_all(coba, 0.6);
        System.out.println(offspring1);
        //System.out.println(coba);
        System.out.println("============");
        System.out.println("mutasi");
        System.out.println("============");
        Map offspring2 = baru.scramble_mutasi(coba, 0.6);
        System.out.println(offspring2);
//        List fitnes = baru.get_fitnes(coba, offspring1, offspring2, data, panjang_bahan);
//        System.out.println(fitnes);
//        int index[] = baru.sort(fitnes);
//        for (int i = 0; i < index.length; i++) {
//        System.out.println(index[i]);    
//        }
//        
////        List a= (List)coba.get(0);
////        for (int i = 0; i < a.size(); i++) {
////            System.out.print(a.get(i)+" ");
////        }
////        System.out.println("");
////        for (int i = 0; i < a.size(); i++) {
////            System.out.print(data.get(a.get(i))+" ");
////        }
////        System.out.println("");
////        System.out.println(baru.hitung_bahan((List)coba.get(0), data, 150));
//        Map populasi = baru.seleksi(coba, offspring1, offspring2, index, 2);
//        System.out.println(populasi);

    }
}
