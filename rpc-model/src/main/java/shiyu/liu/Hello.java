package shiyu.liu;

import lombok.Data;

import java.io.Serializable;

@Data
public class Hello implements Serializable {
    private String message;
}
