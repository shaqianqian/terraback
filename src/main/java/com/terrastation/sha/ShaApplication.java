package com.terrastation.sha;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.*;

@EnableScheduling
@SpringBootApplication
public class ShaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShaApplication.class, args);

//        try{
//            System.out.println("start");
//            Process pr = Runtime.getRuntime().exec("python ../python/chauffage_test.py 1");
//
//            BufferedReader in = new BufferedReader(new
//                    InputStreamReader(pr.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                System.out.println(line);
//            }
//            in.close();
//            pr.waitFor();
//            System.out.println("end");
//        } catch (Exception e){
//            e.printStackTrace();
//        }
    }

//        String cmdStr_linux= "python ../python/chauffage_test.py" + " " + 3;
//        try {
//            Runtime.getRuntime().exec(cmdStr_linux);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        File f = new File("../python","test.txt");
//        try {
//            f.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

 //   }


}
