package com.toan_itc.tn.Utils;

/**
 * Created by vantoan on 2/16/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public enum  GETBITMAP {
  CMND1(1), CMND2(2), HD1(3),HD2(4),PL4(5);
  private int value;
  GETBITMAP(int val){
    value = val;
  }

  public int getValue() {
    return value;
  }
}
