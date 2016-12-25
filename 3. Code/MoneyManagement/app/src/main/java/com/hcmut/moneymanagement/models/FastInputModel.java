package com.hcmut.moneymanagement.models;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hcmut.moneymanagement.R;
import com.hcmut.moneymanagement.activity.FastInput.FastInputAdapter;
import com.hcmut.moneymanagement.objects.FastInput;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by Admin on 24-Dec-16.
 */
public class FastInputModel extends Model{
    private Context context;
    private ArrayList<String> names;
    public ArrayList<FastInput> fastInputs;
    public ArrayList<String> key;
    private FastInputAdapter fastInputAdapter;
    private WalletModel walletModel;
    private Model categoryModel;

    public FastInputModel(){
        names = new ArrayList<String>();
        key = new ArrayList<>();
        fastInputs = new ArrayList<FastInput>();

        // saving refecence
        reference = FirebaseDatabase.getInstance().getReference()
                .child(uidEncrypted).child(encrypt("fastinput"));
        walletModel = new WalletModel();

        // Wallets refecence
        reference.keepSynced(true);
    }

    public void initFastInputAdapter(Activity activity){

        fastInputAdapter = new FastInputAdapter(activity, R.layout.fast_input_item, fastInputs);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fastInputs.clear();
                key.clear();
                // [START_EXCLUDE]
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final FastInput fastInput = new FastInput();
                    //get name
                    Object obj = snapshot.child(encrypt("key")).getValue();
                    fastInput.setKey(decrypt(obj.toString()));

                    //get type (income - expense - saving - transfer)
                    Object objType = snapshot.child(encrypt("type")).getValue();
                    fastInput.setType(decrypt(objType.toString()));

                    //money
                    Object objMoney = snapshot.child(encrypt("money")).getValue();
                    fastInput.setMoney(decrypt(objMoney.toString()));

                    //wallet
                    Object objWallet = snapshot.child(encrypt("wallet")).getValue();
                    DatabaseReference reference = walletModel.getReference().child(objWallet.toString());
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Object objWalletName = dataSnapshot.child(walletModel.encrypt("name")).getValue();
                            fastInput.setWallet(objWalletName.toString());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    //Satrt date
                    Object objCategory = snapshot.child(encrypt("category")).getValue();
                    if( objType.equals("Income") ){
                        categoryModel = new IncomeCategoryModel();
                    }else if( objType.equals("Expense") ){
                        categoryModel = new ExpenseCategoryModel();
                    }else if( objType.equals("Saving") ){
                        categoryModel = new SavingModel();
                    } else {
                        categoryModel = new WalletModel();
                    }

                    //End date
                    Object objDescription = snapshot.child(encrypt("description")).getValue();
                    fastInput.setDescription(decrypt(objDescription.toString()));

                    DatabaseReference itemReference = categoryModel.getReference().child(objCategory.toString());
                    itemReference.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Object objCategoryName = dataSnapshot.child(categoryModel.encrypt("name")).getValue();
                            fastInput.setCategory(decrypt(objCategoryName.toString()));
                            System.out.println("name of category " + fastInput.getMoney());

                            fastInputs.add(fastInput);
                            key.add(snapshot.getKey());
                            fastInputAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
                fastInputAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Load fast input:onCancelled", databaseError.toException());
            }
        });
    }

    // Update a saving
    public void update(String key, Map<String, Object> data){
        reference.child(key).updateChildren(data);
    }

    public FastInputAdapter getFastInputAdapter() {
        return fastInputAdapter;
    }

    public void add(FastInput fastInput){
        Field[] fields = FastInput.class.getFields();
        String key = reference.push().getKey();
        for (int i = 0; i < fields.length; i++){
            try {
                String fieldName = fields[i].getName();

                if( !fieldName.equals("serialVersionUID") && !fieldName.equals("$change")){
                    // Get value object of wallet
                    Object value = fields[i].get(fastInput);
                    if(value != null){
                        String valueEncrypted = encryption.encrypt(value.toString());

                        // Write encypted value to Firebase
                        reference.child(key).child(encrypt(fieldName)).setValue(valueEncrypted);
                    }
                }
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
    }

    public void remove(String key){
        reference.child(key).removeValue();
    }
//
//    public void initNameAdapter(Context context){
//        nameAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, names);
//        //Event Listenner
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                names.clear();
//                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
//
//                    //End date
//                    Object objEndDate = categorySnapshot.child(encrypt("endDate")).getValue();
//                    String endDate1 = decrypt(objEndDate.toString());
//
//                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//                    try {
//                        Date endDate = df.parse(endDate1);
//                        Date now = new Date();
//                        Long leftDate = (endDate.getTime() - now.getTime())
//                                / (24 * 3600 * 1000) + 1;
//                        int i = leftDate.intValue();
//                        if(i < 0) {
//                        } else {
//                            Object objWallet = categorySnapshot.child(encrypt("name")).getValue();
//                            names.add(decrypt(objWallet.toString()));
//                            keys.add(categorySnapshot.getKey());
//                        }
//
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//                nameAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w(TAG, "loadWallet:onCancelled", databaseError.toException());
//            }
//        });
//    }
//
//    public void increaseMoneyAmount(String key, final int amount){
//        final DatabaseReference savingReference =  FirebaseDatabase.getInstance().getReference()
//                .child(uidEncrypted).child(encrypt("saving")).child(key);
//
//        savingReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Object objCurrentAmount = dataSnapshot.child(encrypt("current_amount")).getValue();
//                if(objCurrentAmount != null) {
//                    int currentAmount = Integer.parseInt(decrypt(objCurrentAmount.toString()));
//                    currentAmount += amount;
//                    Map<String, Object> update = new HashMap<String, Object>();
//                    update.put(encrypt("current_amount"), currentAmount);
//
//                    savingReference.updateChildren(update);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//

//


//    public ArrayAdapter<String> getNameAdapter(){
//        if(nameAdapter != null) {
//            return nameAdapter;
//        }
//        return  null;
//    }
//    public SavingAdapter getSavingsFinishAdapter() {
//        return savingsFinishAdapter;
//    }
}
