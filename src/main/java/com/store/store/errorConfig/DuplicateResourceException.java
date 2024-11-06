/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.store.store.errorConfig;

/**
 *
 * @author fernan
 */
public class DuplicateResourceException extends RuntimeException{
     public DuplicateResourceException(String message) {
        super(message);
    }
}
