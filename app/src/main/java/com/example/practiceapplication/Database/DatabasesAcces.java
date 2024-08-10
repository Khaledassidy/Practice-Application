package com.example.practiceapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.practiceapplication.Model.Car;

import java.util.ArrayList;

public class DatabasesAcces {
    private SQLiteOpenHelper OpenHelper;
    private SQLiteDatabase database;
    private static DatabasesAcces instance;

    private DatabasesAcces(Context context){
        this.OpenHelper=new Mydatabase(context);
    }
    public static DatabasesAcces getInstance(Context context){
        if(instance==null){
            instance=new DatabasesAcces(context);
        }
        return instance;
    }

    public void open(){
        this.database=this.OpenHelper.getWritableDatabase();
    }

    public void close(){
        //ba3mel check iza databse maftou7a fe 7ada 3am yest5dema sakera
        if(database!=null){
            this.database.close();
            //oltelo rou7 3al open helper le howe 2asln database ta3ele mydatabase w sakro
        }
    }

    public boolean insertCar(Car car) {
        ContentValues values = new ContentValues();
        values.put(Mydatabase.CAR_TAB_MODEL, car.getModel_car());
        values.put(Mydatabase.CAR_TAB_image,car.getImage_car());
        values.put(Mydatabase.CAR_TAB_COLOR, car.getColor_car());
        values.put(Mydatabase.CAR_TAB_DPL, car.getDpl_car());
        values.put(Mydatabase.CAR_TAB_description, car.getDescription_car());

        long result = database.insert(Mydatabase.CAR_TAB_NAME, null, values);
        //OR
        //db.execSQL("INSERT INTO car (model,color,distanceforlitter) VALUES ("+car.getMode()+","+car.getColor()+","+car.getDpl()+")");
        return result != -1;
    }

    public boolean updateCar(Car car) {
        ContentValues values = new ContentValues();
        values.put(Mydatabase.CAR_TAB_MODEL, car.getModel_car());
        values.put(Mydatabase.CAR_TAB_image, car.getImage_car());
        values.put(Mydatabase.CAR_TAB_COLOR, car.getColor_car());
        values.put(Mydatabase.CAR_TAB_DPL, car.getDpl_car());
        values.put(Mydatabase.CAR_TAB_description, car.getDescription_car());
        String[] args = {String.valueOf(car.getId())};
        //car.getId()+"" or tostring() class l string ya3ne
        int result = database.update(Mydatabase.CAR_TAB_NAME, values, "id=?", args);
        //OR
        //ba3mel db.exc(update set....etc)
        return result > 0;
    }

    public long getCarsCount() {
        long result = DatabaseUtils.queryNumEntries(database, Mydatabase.CAR_TAB_NAME);
        //law badak testa3mel selection w selection arg:
        //String [] args ={"10"};
        //long result=DatabaseUtils.queryNumEntries(db,CAR_TAB_NAME,"id=?",args);
        //raj3le 3adad l rows le l id ta3eloun equll 10

        //or tare2a tenye:
        //db.execSQL("SELECT COUNT(*) FROM "+CAR_TAB_NAME);
        return result;
    }

    public boolean DeleteCar(Car car) {
        String[] args = {String.valueOf(car.getId())};
        int result = database.delete(Mydatabase.CAR_TAB_NAME, "id=?", args);

        //feek enta mesh darore teb3at l car ka object yemken teb3at String x w b2alb l array int ars={"%x%"} w ta3bel db.edlete(Car_tab_name,"model LIKE ?" ,args ya3ne 7zefle l row le l model like jomle l user b faweta ya3ne la n2ol l x kenet 2020 fa 3mle delete la row le model ta3ela b2lbo 2020 metl ya3ne iza keen l model dededed2020dfee 3mle delete la heda row

        //w feek ta3mel delete btare2a tenye le heye excute qury ya3ne db.excsql("Delete...etc")
        return result > 0;

    }

    public ArrayList<Car> GetAllCars() {
        ArrayList<Car> cars = new ArrayList<>();
        //know cursor in row -1 w hay jomle kola raj3lek table metl le mawjoud bel database mra2m rom mn 0 w l column 0 w howe bekoun 2awl ma 3mlne 3ala l -1
        Cursor cursor = database.rawQuery("SELECT * FROM " + Mydatabase.CAR_TAB_NAME, null);
        // hay fa7stlk eno hal fe keyam bel cursor wala la2 w iza l 3onour l 2awl mawjoud 2aw la2 l2no l cursor lezm 2awl ma yerja3 ykoun fe b2labo kel table ma howe berja3 data metl shakl dataabse table w cursor.move.tofirst btshoflak bten2lak 3ala 2awl 3onour l2no 2awl ma na3ml l cursor bekoun 3al -1 w btrj3lk true iza fe b2alb 2awl row data iza la2 btrje3 false  iza hala2 l cursor 3ned l 3onsour l 2awal l 0 row 0 l move to first btrj3lak boolean ya3ne iza nta2al 3end 2awl 3nousur w fe 3osnour berj3lak treu iza ma fe 3osnour false ya3ne fene 2olo iza l cirsor.movetofirst raja3 true jeble meno data iza false
        if (cursor != null && cursor.moveToFirst()) {
            //hala2 bjeeb data:
            //2awl she l id l id integer so getint() w ba3te l index taba3 l id le howe 0:
            do {
                //2awl she l id l id integer so getint() w ba3te l index taba3 l id le howe 0:
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(Mydatabase.CAR_TAB_ID));
                //l model  string so getstring() w ba3te l index taba3 l model le howe 1:
                String image = cursor.getString(cursor.getColumnIndexOrThrow(Mydatabase.CAR_TAB_image));

                String model = cursor.getString(cursor.getColumnIndexOrThrow(Mydatabase.CAR_TAB_MODEL));
                //l color string so getstring() w ba3te l index taba3 l color le howe 2:


                String color = cursor.getString(cursor.getColumnIndexOrThrow(Mydatabase.CAR_TAB_COLOR));
                //l dpl double so getdouble() w ba3te l index taba3 l dpl le howe 3:
                double dpl = cursor.getDouble(cursor.getColumnIndexOrThrow(Mydatabase.CAR_TAB_DPL));

                String description=cursor.getString(cursor.getColumnIndexOrThrow(Mydatabase.CAR_TAB_description));
                //hala2 ana bade raje3 l data ka array list so kel mara bye2rha ba3mel object car w b7eta bel arraylist
                Car car = new Car(id, image, model, color,dpl,description);
                cars.add(car);
            } while (cursor.moveToNext());

            //hala2 mafroud ba3d ma t5les kera2a mn cursor tsaker l cursor cursor.close() hala2 howe telk2eyan beskro bas y5les bas l a7san tsakro yadaweyan
            cursor.close();

            //hala2 bel move:
            //3ande movetopostion() bta3te aye postion berou7 3lyha
            //3ande moveto() bta3te kamen postion perou7 3lyha


        }
        return cars;


    }

    public ArrayList<Car> SearchCars(String modelSearch) {
        ArrayList<Car> cars = new ArrayList<>();
        //know cursor in row -1 w hay jomle kola raj3lek table metl le mawjoud bel database mra2m rom mn 0 w l column 0 w howe bekoun 2awl ma 3mlne 3ala l -1
        Cursor cursor = database.rawQuery("SELECT * FROM " + Mydatabase.CAR_TAB_NAME + " WHERE " + Mydatabase.CAR_TAB_MODEL+" LIKE ?", new String[]{"%"+modelSearch+"%"});
        // hay fa7stlk eno hal fe keyam bel cursor wala la2 w iza l 3onour l 2awl mawjoud 2aw la2 l2no l cursor lezm 2awl ma yerja3 ykoun fe b2labo kel table ma howe berja3 data metl shakl dataabse table w cursor.move.tofirst btshoflak bten2lak 3ala 2awl 3onour l2no 2awl ma na3ml l cursor bekoun 3al -1 w btrj3lk true iza fe b2alb 2awl row data iza la2 btrje3 false  iza hala2 l cursor 3ned l 3onsour l 2awal l 0 row 0 l move to first btrj3lak boolean ya3ne iza nta2al 3end 2awl 3nousur w fe 3osnour berj3lak treu iza ma fe 3osnour false ya3ne fene 2olo iza l cirsor.movetofirst raja3 true jeble meno data iza false
        if (cursor != null && cursor.moveToFirst()) {
            //hala2 bjeeb data:
            //2awl she l id l id integer so getint() w ba3te l index taba3 l id le howe 0:
            do {
                //2awl she l id l id integer so getint() w ba3te l index taba3 l id le howe 0:
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(Mydatabase.CAR_TAB_ID));
                //l model  string so getstring() w ba3te l index taba3 l model le howe 1:
                String model = cursor.getString(cursor.getColumnIndexOrThrow(Mydatabase.CAR_TAB_MODEL));
                //l color string so getstring() w ba3te l index taba3 l color le howe 2:
                String image = cursor.getString(cursor.getColumnIndexOrThrow(Mydatabase.CAR_TAB_image));

                String color = cursor.getString(cursor.getColumnIndexOrThrow(Mydatabase.CAR_TAB_COLOR));
                //l dpl double so getdouble() w ba3te l index taba3 l dpl le howe 3:
                double dpl = cursor.getDouble(cursor.getColumnIndexOrThrow(Mydatabase.CAR_TAB_DPL));

                String description=cursor.getString(cursor.getColumnIndexOrThrow(Mydatabase.CAR_TAB_description));

                //hala2 ana bade raje3 l data ka array list so kel mara bye2rha ba3mel object car w b7eta bel arraylist
                Car car = new Car(id, image, model, color,dpl,description);
                cars.add(car);
            } while (cursor.moveToNext());

            //hala2 mafroud ba3d ma t5les kera2a mn cursor tsaker l cursor cursor.close() hala2 howe telk2eyan beskro bas y5les bas l a7san tsakro yadaweyan
            cursor.close();

            //hala2 bel move:
            //3ande movetopostion() bta3te aye postion berou7 3lyha
            //3ande moveto() bta3te kamen postion perou7 3lyha


        }

        return cars;


    }

    public Car GetCar(int id_car) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + Mydatabase.CAR_TAB_NAME+" WHERE "+Mydatabase.CAR_TAB_ID+" = ?",new String[]{String.valueOf(id_car)});
        if (cursor != null && cursor.moveToFirst()) {

                int id = cursor.getInt(cursor.getColumnIndexOrThrow(Mydatabase.CAR_TAB_ID));
                String model = cursor.getString(cursor.getColumnIndexOrThrow(Mydatabase.CAR_TAB_MODEL));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(Mydatabase.CAR_TAB_image));
                String color = cursor.getString(cursor.getColumnIndexOrThrow(Mydatabase.CAR_TAB_COLOR));
                double dpl = cursor.getDouble(cursor.getColumnIndexOrThrow(Mydatabase.CAR_TAB_DPL));
                String description=cursor.getString(cursor.getColumnIndexOrThrow(Mydatabase.CAR_TAB_description));
                Car car = new Car(id, image, model, color,dpl,description);


            cursor.close();
            return car;




        }
        return null;


    }




}
