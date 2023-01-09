package com.example.projekt

class ExerciseModel {
    private var date: String = ""
    private var amount: Int = 0;
    private var id: Int = 0;

    public fun getDate() : String
    {
        return date;
    }

    public fun getAmount() : Int
    {
        return amount
    }

    public fun getId(): Int{
        return id
    }

    public fun setId(id: Int){
        this.id = id;
    }
    constructor(date: String, amount: Int)
    {
        this.amount = amount
        this.date = date
    }
}