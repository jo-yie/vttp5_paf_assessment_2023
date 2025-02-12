package vttp2023.batch3.assessment.paf.bookings.repositories;

public class Queries {

    public static final String SQL_GET_VACANCY =
    """
        SELECT vacancy FROM acc_occupancy
            WHERE acc_id = ?
    """;

    public static final String SQL_INSERT_BOOKING = 
    """
        INSERT INTO reservations (resv_id, name, email, acc_id, arrival_date, duration)
            VALUES (?, ?, ?, ?, ?, ?)        
    """;

    public static final String SQL_UPDATE_VACANCY = 
    """
        UPDATE acc_occupancy
            SET vacancy = vacancy - ?
            WHERE acc_id = ?
    """;
    
}
