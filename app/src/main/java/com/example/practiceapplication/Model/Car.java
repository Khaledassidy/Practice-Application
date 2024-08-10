package com.example.practiceapplication.Model;

public class Car {

    private int id;
    private String image_car;
    private String model_car;
    private String color_car;
    private double dpl_car;
    private String description_car;

    public Car(int id,String image_car,String model_car,String color_car,double dpl_car,String description_car){
        this.id=id;
        this.image_car=image_car;
        this.model_car=model_car;
        this.color_car=color_car;
        this.dpl_car=dpl_car;
        this.description_car=description_car;
    }


    public Car(String image_car,String model_car,String color_car,double dpl_car,String description_car){
        this.image_car=image_car;
        this.model_car=model_car;
        this.color_car=color_car;
        this.dpl_car=dpl_car;
        this.description_car=description_car;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_car() {
        return image_car;
    }

    public void setImage_car(String image_car) {
        this.image_car = image_car;
    }

    public String getModel_car() {
        return model_car;
    }

    public void setModel_car(String model_car) {
        this.model_car = model_car;
    }

    public String getColor_car() {
        return color_car;
    }

    public void setColor_car(String color_car) {
        this.color_car = color_car;
    }

    public double getDpl_car() {
        return dpl_car;
    }

    public void setDpl_car(double dpl_car) {
        this.dpl_car = dpl_car;
    }

    public String getDescription_car() {
        return description_car;
    }

    public void setDescription_car(String description_car) {
        this.description_car = description_car;
    }
}
