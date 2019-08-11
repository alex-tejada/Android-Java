package com.developer.tonny.design.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.developer.tonny.design.Fragments.FirstFragment;
import com.developer.tonny.design.Fragments.SecondFragment;
import com.developer.tonny.design.Fragments.ThirdFragment;

/**
 * Clase para adaptar las secuenias de ventana en la vista principal
 * @author Alejandro
 * @version 1.0
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    /**
     * Constructor de la clase
     * @param fm FragmentManager manejador de ventana
     * @param numberOfTabs int Numero de pantallas en la ventana
     */
    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    // Cambio de tabs

    /**
     * Metodo para obtener el fragment solicitado
     * @param position int Posicion del fragment
     *
     */
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FirstFragment();
            case 1:
                return new SecondFragment();
            case 2:
                return new ThirdFragment();
            default:
                return null;
        }
    }

    // Numero de tabs

    /**
     * Metodo para devolver el numero de pantallas en una ventana
     * @return numberOfTabs int Numero de pantallas en una ventana
     */
    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
