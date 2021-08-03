package com.company;

import java.io.File;

class Criteria {
    private String  path;
    private String extension;

    public Criteria(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    public String getPath() {
        return path;
    }

    public String getExtension() {
        return extension;
    }
}

public class Count {

    public static void count(Criteria criteria) {

        File f = new File(criteria.getPath());
        File[] files = f.listFiles();
        int fileCount = 0;
        int folderCount = 0;

        try {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    folderCount++;
                    count(new Criteria(files[i].getAbsolutePath(), criteria.getExtension()));
                } else {
                    if (files[i].getName().toLowerCase().endsWith(criteria.getExtension())) {
                        fileCount++;
                    }
                }
            }
            System.out.printf("There are %d file(s) and %d folder(s) inside folder %s with extension %s. %n", fileCount, folderCount, criteria.getPath(), criteria.getExtension());

        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
            System.out.println("Invalid input!");
        }

    }

    public static void main(String[] args) {
        Criteria c = new Criteria("/Users/jzhong7/Downloads/Antra", ".docx");
        count(c);
    }
}
