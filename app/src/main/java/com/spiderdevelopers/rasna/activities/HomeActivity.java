package com.spiderdevelopers.rasna.activities;

import android.app.Fragment;
import android.provider.SyncStateContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.extras.Constatnts;
import com.spiderdevelopers.rasna.fragments.FragmentCart;
import com.spiderdevelopers.rasna.fragments.FragmentItem;
import com.spiderdevelopers.rasna.fragments.FragmentItemCustom;
import com.spiderdevelopers.rasna.fragments.FragmentMenu;
import com.spiderdevelopers.rasna.fragments.FragmentNotification;
import com.spiderdevelopers.rasna.fragments.FragmentOrderHistory;
import com.spiderdevelopers.rasna.fragments.FragmentOrderSummery;
import com.spiderdevelopers.rasna.fragments.FragmentPayment;
import com.spiderdevelopers.rasna.fragments.FragmentProfile;
import com.spiderdevelopers.rasna.fragments.FragmentRestList;
import com.spiderdevelopers.rasna.fragments.Fragment_Address;

public class HomeActivity extends BaseActivity implements AHBottomNavigation.OnTabSelectedListener {

    private static final String TAG = "HomeActivity";
    AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.e(TAG,"user_id -> "+preferenceManager.getInt(Constatnts.LOGIN_ID));

        bindViews();

        setupToolbar();
        //default Fragment
        changeFragment(Constatnts.FRAGMENT_REST_LIST, getResources().getString(R.string.str_menu), false, false, null);
    }

    public void changeFragment(int fragmentId, String title, boolean isBackStack, boolean isPopBack, Bundle bundle) {
        android.support.v4.app.Fragment fragment;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (fragmentId) {
            default:
                fragment = new FragmentRestList();
            case Constatnts.FRAGMENT_REST_LIST:
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragment = new FragmentRestList();
                break;
            case Constatnts.FRAGMENT_MENU:
                fragment = new FragmentMenu();
                break;
            case Constatnts.FRAGMENT_ITEM:
                fragment = new FragmentItem();
                break;
            case Constatnts.FRAGMENT_ITEM_CUSTOMIZATION:
                fragment = new FragmentItemCustom();
                break;

            case Constatnts.FRAGMENT_CART:
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragment = new FragmentCart();
                break;
            case Constatnts.FRAGMENT_ADDRESS:
                fragment = new Fragment_Address();
                break;
            case Constatnts.FRAGMENT_ORDER_SUMMERY:
                fragment = new FragmentOrderSummery();
                break;
            case Constatnts.FRAGMENT_PAYMENT:
                fragment = new FragmentPayment();
                break;
            case Constatnts.FRAGMENT_ORDER_HISTORY:
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragment = new FragmentOrderHistory();
                break;
            case Constatnts.FRAGMENT_PROFILE:
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragment = new FragmentProfile();
                break;
            case Constatnts.FRAGMENT_NOTIFICATION:
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragment = new FragmentNotification();
                break;


        }

        if (isPopBack) {
            fm.popBackStack();
        }
        if (isBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        if(bundle!=null)
            fragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragmentMain, fragment);
        fragmentTransaction.commit();
    }

    private void bindViews() {
        bottomNavigation = findViewById(R.id.bottom_navigation);

        setupBottomView();
    }

    private void setupBottomView() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getResources().getString(R.string.str_menu), R.drawable.ic_menu, R.color.text_color);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getResources().getString(R.string.str_orders), R.drawable.ic_order, R.color.text_color);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getResources().getString(R.string.str_notification), R.drawable.ic_notification, R.color.text_color);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(getResources().getString(R.string.str_cart), R.drawable.ic_cart, R.color.text_color);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(getResources().getString(R.string.str_profile), R.drawable.ic_profile, R.color.text_color);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);

        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        bottomNavigation.setNotification("1", 2);

        bottomNavigation.setOnTabSelectedListener(this);
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {

        switch (position) {
            case 0:
                changeFragment(Constatnts.FRAGMENT_REST_LIST, "", false, false, null);
                break;
            case 1:
                changeFragment(Constatnts.FRAGMENT_ORDER_HISTORY, "", false, false, null);
                break;
            case 2:
                changeFragment(Constatnts.FRAGMENT_NOTIFICATION, "", false, false, null);
                break;

            case 3:
                changeFragment(Constatnts.FRAGMENT_CART, "", false, false, null);
                break;
            case 4:
                changeFragment(Constatnts.FRAGMENT_PROFILE, "", false, false, null);
                break;


        }
        return true;
    }
}
