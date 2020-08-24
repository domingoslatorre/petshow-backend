package hyve.petshow.factory;

import hyve.petshow.domain.Teste;
import org.springframework.beans.factory.FactoryBean;

public class TesteFactory implements FactoryBean<Teste> {
    @Override
    public Teste getObject() throws Exception {
        return new Teste();
    }

    @Override
    public Class<Teste> getObjectType() {
        return Teste.class;
    }
}
