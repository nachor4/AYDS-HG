package com.unrc.app;

import com.unrc.app.models.RealEstate;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;

public class RealEstateSpec{

    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "");
        Base.openTransaction();
    }

    @After
    public void after(){
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){

        RealEstate realestate = new RealEstate();

        //check errors
        the(realestate).shouldNotBe("valid");
        the(realestate.errors().get("name")).shouldBeEqual("value is missing");
        the(realestate.errors().get("city")).shouldBeEqual("value is missing");
        the(realestate.errors().get("street")).shouldBeEqual("value is missing");
        the(realestate.errors().get("email")).shouldBeEqual("value is missing");

        //set missing values
        realestate.set("name", "John");
        realestate.set("city", "rio cuarto");
        realestate.set("street", "rivadavia");
		realestate.set("email","algo@algod.com");
		
        //all is good:
        the(realestate).shouldBe("valid");
    }
}
