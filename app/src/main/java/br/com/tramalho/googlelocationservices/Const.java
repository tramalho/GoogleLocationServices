package br.com.tramalho.googlelocationservices;

/**
 * Created by trama on 03/08/16.
 */
public interface Const {

    String PACKAGE = Const.class.getPackage().getName();

    String BROADCAST_ACTION = PACKAGE.concat(".ACTIVITIES_BROADCAST");
    String PROBABLE_ACTIVIES_EXTRA = BROADCAST_ACTION.concat("PROBABLE_ACTIVIES_EXTRA");

}
