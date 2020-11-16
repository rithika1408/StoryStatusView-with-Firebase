package com.example.statusstory;

import java.util.List;

public interface Firebase {
    void onFirebaseLoadsuccess(List<Upload> uploadList);
    void onFirebaseLoadFailed(String message);
}
