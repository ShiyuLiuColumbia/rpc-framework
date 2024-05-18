package shiyu.liu.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import shiyu.liu.Hello;
import shiyu.liu.model.RpcRequest;
import shiyu.liu.model.RpcResponse;

import java.io.ByteArrayOutputStream;

public class KryoSerializerV1 {
    private final Kryo kryo;
    public KryoSerializerV1() {
        kryo = new Kryo();
        kryo.setRegistrationRequired(false);
    }

    public byte[] serialize(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Output kryoOutPut = new Output(byteArrayOutputStream);
        kryo.writeObject(kryoOutPut, object);
        return kryoOutPut.toBytes();
    }

    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        Input input = new Input(bytes);
        return kryo.readObject(input, clazz);
    }
}
