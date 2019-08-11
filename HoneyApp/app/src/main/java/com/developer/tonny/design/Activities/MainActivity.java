package com.developer.tonny.design.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.developer.tonny.design.Adapters.PagerAdapter;
import com.developer.tonny.design.Database;
import com.developer.tonny.design.R;

/**
 *
 * @author Alejandro
 * @version 1.0
 *
 */
public class MainActivity extends AppCompatActivity {


    public static boolean Iniciado=false;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter adapter;
    public static String usuarioNombre="";

    private static final int Intervalo = 2000;
    private long tiempoPrimerClick;

    private DrawerLayout drawerLayout;
    NavigationView nv;

    public static Database db;

    /**
     * Metodo constructor para la clase
     * @param savedInstanceState Bundle Datos de componentes
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try
        {
            db = new Database(getApplicationContext());
            getLogged();
        }
        catch (Exception e)
        {
           Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle("My HoneyApp");
        setToolbar();
        setTabLayout();
        setViewPager();
        setListenerTabLayout(viewPager);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        nv = (NavigationView) findViewById(R.id.navView);


        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_mail:
                        if(Iniciado){
                            Intent login = new Intent(getApplicationContext(), logoutActivity.class);
                            startActivity(login);
                        }
                        else {
                            Intent login = new Intent(getApplicationContext(), loginActivity.class);
                            startActivity(login);
                        }
                        return true;
                    case R.id.menu_medicamentos:
                        Intent calc = new Intent(getApplicationContext(), Medicamentos.class);
                        startActivity(calc);
                        return true;
                    case R.id.menu_emergencias:
                        Intent emergencia = new Intent(getApplicationContext(), Citas.class);
                        startActivity(emergencia);
                        return true;

                    case R.id.menu_mapa:
                        Intent mapa= new Intent(getApplicationContext(), mapaActivity.class);
                        startActivity(mapa);
                        return true;
                    default:
                        return false;
                }
            }
        });
        Comprobar(Iniciado);
    }
    public void getLogged()
    {
        Cursor cursor =db.getRegistrySELECT();

        if(cursor.getCount()==0)
        {

        }
        else
        {

            if(cursor.moveToNext())
            {
                usuarioNombre=cursor.getString(1);
                Iniciado=true;
                //Toast.makeText(getApplicationContext(),MainActivity.usuarioNombre,Toast.LENGTH_LONG).show();

            }
            try
            {
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            }
        }
    }


    // Agregamos el toolbar creado
    /**
     * Metodo para crear la barra de herramientas
     */
    private void setToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    // Añádimos los tabs

    /**
     * Metodo para crear la barra de herramientas
     */
    private void setTabLayout() {

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.titleRegisttry).setIcon(R.drawable.iconregistry));/*R.string.titleRegisttry)*/
        tabLayout.addTab(tabLayout.newTab().setText(R.string.titleMedicines).setIcon(R.drawable.iconmedicine));/*R.string.titleMedicines)*/
        tabLayout.addTab(tabLayout.newTab().setText(R.string.titleDates).setIcon(R.drawable.icondates));/*R.string.titleDates)*/
    }

    // El adaptador nos permite acceder a la clase y adjuntamos el adaptador

    /**
     * Metodo para crear la vista de la pantalla de las ventanas
     *
     */
    private void setViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    // Cuando haya una accion sobre el tab

    /**
     * Metodo para obtener los eventos de cambio de ventana
     * @param viewPager ViewPager vista de la pantalla
     */
    private void setListenerTabLayout(final ViewPager viewPager) {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            // Muestra el tab seleccionado
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            // Se mueve de un tab a otro
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            // Ha sido reelegido el mismo tab
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    // Menu de opciones

    /**
     * Metodo constructor para crear el menu
     * @param menu Menu Obtiene los parametros del menu
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Metodo constructor para la seleccion de opciones de un menu
     * @param item MenuItem regresa la opcion seleccionada
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

            case R.id.menu_ajustes:
                Intent ajustes = new Intent(getApplicationContext(), Ajustes.class);
                startActivity(ajustes);
                return true;

            case android.R.id.home:
                // abrir el menu lateral
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Metodo para comprobar el tipo de usuario
     * @param usertype boolean Usuario valido o registrado
     */
    public void Comprobar(boolean usertype)
    {
       if(usertype){
               nv.getMenu().findItem(R.id.menu_mail).setTitle(R.string.menu_mailLogout);

       }
       else{
               nv.getMenu().findItem(R.id.menu_mail).setTitle(R.string.menu_mailLogin);

       }
    }
}
