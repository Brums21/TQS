package lab7_1;

import static org.junit.Assert.assertTrue;

import org.junit.Before;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import io.restassured.RestAssured;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    
    @Before
    public void setup(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

    }

    @Test
    public void shouldAnswerWithTrue()
    {
        when().
            get("/todos").
        then().
            statusCode(200); //working as intended
    }    
    
    @Test 
    public void ifToDo4ThenReturnTitle(){
        when().
            get("/todos/4").
        then().
            assertThat().
            body("title", equalTo("et porro tempora"));
    }

    @Test
    public void ifToDoID198And199(){
        when().get("/todos").then()
        .body("id", hasItems(198, 199));
    }
    
    @Test
    public void whenList_2SecResults(){
        when().get("/todos").then().time(lessThan(2000L));
    }
}
