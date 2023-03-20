

package Tests;

import model.BookerAuthRequest;
import model.BookerAuthResponse;
import model.Booking;
import model.Booking.BookingDates;
import model.BookingCreateResponse;
import model.BookingId;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertTrue;

public class APItest {

    private static final String BASE_URI = "https://restful-booker.herokuapp.com";
    private static final String AUTH_URL = "/auth";
    private static final String BOOKING_URL = "/booking";

    private Map<String, String> headers;

    @BeforeClass
    public void testsSetUp() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .build();

        this.headers = new HashMap<>() {{
            put("Content-Type", "application/json");
            put("Accept", "application/json");
        }};
    }

    @Test
    public void Check() {
        // Create token
        var bookerAuthResponse = given()
                .headers(headers)
                .body(new BookerAuthRequest("admin", "password123"))
                .when()
                .post(AUTH_URL)
                .then()
                .statusCode(200)
                .extract().body().as(BookerAuthResponse.class);

        this.headers.put("Cookie", "token=" + bookerAuthResponse.getToken());

        // Create booking
        var bookingCreateResponse = given()
                .headers(headers)
                .body(getBooking(111))
                .when()
                .post(BOOKING_URL)
                .then()
                .statusCode(200)
                .extract().body().as(BookingCreateResponse.class);

        // Fetch booking after creation
        given()
                .headers(headers)
                .when()
                .get(BOOKING_URL + "/" + bookingCreateResponse.getBookingid())
                .then()
                .statusCode(200)
                .body("firstname", is("Jim"))
                .body("lastname", is("Brown"))
                .body("totalprice", is(111))
                .body("depositpaid", is(true))
                .body("bookingdates.checkin", is("2018-01-01"))
                .body("bookingdates.checkout", is("2019-01-01"))
                .body("additionalneeds", is("Breakfast"));

        // Update total price in booking
        given()
                .headers(headers)
                .body(getBooking(152))
                .when()
                .put(BOOKING_URL + "/" + bookingCreateResponse.getBookingid())
                .then()
                .statusCode(200);

        // Fetch booking after updating
        var bookingIds = given()
                .headers(headers)
                .when()
                .get(BOOKING_URL)
                .then()
                .statusCode(200)
                .extract().body().as(new TypeRef<List<BookingId>>() {});

        boolean containsBookingId = bookingIds.stream()
                .map(BookingId::getBookingid)
                .collect(Collectors.toSet())
                .contains(bookingCreateResponse.getBookingid());

        assertTrue(containsBookingId);

        // Fetch all booking
        given()
                .headers(headers)
                .when()
                .get(BOOKING_URL + "/" + bookingCreateResponse.getBookingid())
                .then()
                .statusCode(200)
                .body("firstname", is("Jim"))
                .body("lastname", is("Brown"))
                .body("totalprice", is(152))
                .body("depositpaid", is(true))
                .body("bookingdates.checkin", is("2018-01-01"))
                .body("bookingdates.checkout", is("2019-01-01"))
                .body("additionalneeds", is("Breakfast"));

        // Delete booking
        given()
                .headers(headers)
                .when()
                .delete(BOOKING_URL + "/" + bookingCreateResponse.getBookingid())
                .then()
                .statusCode(201);
    }

    private static Booking getBooking(Integer totalPrice) {
        return Booking.builder()
                .firstname("Jim")
                .lastname("Brown")
                .totalprice(totalPrice)
                .depositpaid(true)
                .bookingdates(new BookingDates(
                        LocalDate.of(2018, 1, 1).toString(),
                        LocalDate.of(2019, 1, 1).toString()
                ))
                .additionalneeds("Breakfast")
                .build();
    }
}
