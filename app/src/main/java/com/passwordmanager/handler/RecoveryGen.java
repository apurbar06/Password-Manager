package com.passwordmanager.handler;

import android.content.Context;
import android.text.TextUtils;

import com.passwordmanager.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class RecoveryGen {
    private static final int MAX_ASCII_CHARACTER_ALLOWED = 94;
    private static final int ASCII_CHARACTER_PADDING_START = 32;
    public ArrayList<ArrayList<String>> words = new ArrayList<>();
    public ArrayList<Integer> used_words = new ArrayList<>();


    public RecoveryGen(Context ctx) throws IOException {
        InputStream stream = ctx.getResources().openRawResource(R.raw.english);
        InputStreamReader inputReader = new InputStreamReader(stream);
        BufferedReader buffReader = new BufferedReader(inputReader);

        String l, data = "";
        try {
            while ((l = buffReader.readLine()) != null) {
                data += l;
                data += '\n';
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] lines = data.split("\n");
        for (String line : lines) {
            String[] line_words = line.split(" ");
            ArrayList<String> lines_arr = new ArrayList<>();
            Collections.addAll(lines_arr, line_words);
            this.words.add(lines_arr);
            this.used_words.add(0);
        }


//        new BaseStorageHandler( R.raw.english)
    }

    public static void main(String[] args) {
        // this.words = wordList.split("\n").map(e => e.split(" "));
        // this.used_words = new Array(this.words.length).fill(0);
    }

    private String encryptWord(String character) {
        // remove padding for non all those control characters @see
        // ASCII_CHARACTER_PADDING_START
        int char_index = character.toCharArray()[0] - RecoveryGen.ASCII_CHARACTER_PADDING_START;
        // extract word list based on index from sorted word dictionary
        ArrayList<String> word_list = this.words.get(char_index);
        // get position of used word and module it for warping to array first
        int prev_pos = this.used_words.get(char_index) % word_list.size();
        String return_word = word_list.get(prev_pos);
        // console.log(`(${prev_pos}),(${return_word})`);
        // update used word index
        this.used_words.set(char_index, prev_pos + 1);
        // return the word
        return return_word;
    }

    private String decryptWord(String word) {
        int word_number = 0;
        // convert word to array of character
        ArrayList<String> words = convertStringToArray(word);

        // convert array of character to array of ascii position
        for (String w : words) {
            // sum all ascii position
            word_number += w.toCharArray()[0];
        }
        // cap the ascii position to MAX_ASCII_CHARACTER_ALLOWED .
        // because other ascii character are not printable
        int ascii_position = word_number % RecoveryGen.MAX_ASCII_CHARACTER_ALLOWED;
        return String.valueOf((char) (32 + ascii_position));
    }

    public ArrayList<String> convertStringToArray(String str) {
        ArrayList<String> words = new ArrayList<>();
        char[] plainChar = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            String s = String.format("%s", plainChar[i]);
            words.add(s);
        }
        return words;
    }

    public String encryptString(String plainText) {
        ArrayList<String> plainWords = convertStringToArray(plainText);
        ArrayList<String> words = new ArrayList<>();
        for (String w : plainWords) {
            words.add(this.encryptWord(w));
        }
        return TextUtils.join(" ", words);
    }

    public String decryptString(String encryptText) {
        String[] plainWords = encryptText.trim().split(" ");
        StringBuilder word = new StringBuilder();
        for (String w : plainWords) {
            word.append(this.decryptWord(w));
        }
        return word.toString();
    }

}