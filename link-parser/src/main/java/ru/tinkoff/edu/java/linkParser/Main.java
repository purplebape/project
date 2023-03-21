package ru.tinkoff.edu.java.linkParser;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws MalformedURLException {

        Scanner in = new Scanner(System.in);
        System.out.println("Введите ссылку: ");
        String url = in.nextLine();

        //https://github.com/sanyarnd/tinkoff-java-course-2022/
        //https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c
        //https://youtube.com/

        LinkParser githubParser = new GithubParser();
        LinkParser stackOverflowParser = new StackOverflowParser();
        githubParser.setNextParser(stackOverflowParser);

        String[] result = githubParser.parse(url);
        System.out.println(Arrays.toString(result));
    }
}
