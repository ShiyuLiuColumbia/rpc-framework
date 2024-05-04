package shiyu.liu.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
}
