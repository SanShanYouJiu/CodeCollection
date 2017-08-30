package javaHighConcurrentDesign.chapter5.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by www85 on 2017/8/18.
 */
public class PCDataFactory implements EventFactory<PCData> {

    @Override
    public PCData newInstance() {
        return new PCData();
    }
}
