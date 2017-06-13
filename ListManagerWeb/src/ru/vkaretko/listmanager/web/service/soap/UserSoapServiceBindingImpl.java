package ru.vkaretko.listmanager.web.service.soap;

import java.util.List;
import javax.xml.ws.Action;


@javax.jws.WebService (endpointInterface="ru.vkaretko.listmanager.web.service.soap.UserSoapService", targetNamespace="http://soap.service.web.listmanager.vkaretko.ru/", serviceName="UserSoapService", portName="UserSoapService")
public class UserSoapServiceBindingImpl{

    public Long getUserId(String userName) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<ListItem> getUserListItems(Long userId) {
        // TODO Auto-generated method stub
        return null;
    }

}