package com.thangtien.firstapp.ultil;


import com.thangtien.firstapp.model.ProductType;

import java.util.ArrayList;
import java.util.List;

public class RXAndroid {
//    public static Observable<ProductType> getObservableProduct(){
//        List<ProductType> list = getListProductType();
//        return Observable.c
//    }

    private static List<ProductType> getListProductType() {
        List<ProductType> list = new ArrayList<>();
        list.add(new ProductType(1, "ABC", "ABC"));
        list.add(new ProductType(1, "EFG", "EFG"));
        list.add(new ProductType(1, "QWE", "QWE"));
        return list;
    }
}
