package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Reader extends Actor{

    // Constructors -----------------------------------------------------------

    public Reader(){
        super();
    }

    // Attributes -------------------------------------------------------------

    // Relationships ----------------------------------------------------------

}
