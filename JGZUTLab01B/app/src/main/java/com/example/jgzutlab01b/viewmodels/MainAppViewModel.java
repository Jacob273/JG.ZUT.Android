package com.example.jgzutlab01b.viewmodels;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import java.util.Objects;

public class MainAppViewModel extends ViewModel {

    private SavedStateHandle state;

    private String currentBmi;

    public MainAppViewModel(SavedStateHandle savedStateHandle) {
        state = savedStateHandle;
    }

    public void setBmi(String newBMI)
    {
        currentBmi = Objects.toString(newBMI, "");
    }

    public String getBmi()
    {
        return currentBmi;
    }


}
