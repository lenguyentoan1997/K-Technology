/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author LE NGUYEN TOAN
 */
public class Producer {

    private String producerName, producerType, producerAdd, producerPhone;
    private int producerNumberRow;

    public Producer() {
    }

    public Producer(String producerName, String producerType, String producerAdd, String producerPhone, int producerNumberRow) {
        this.producerName = producerName;
        this.producerType = producerType;
        this.producerAdd = producerAdd;
        this.producerPhone = producerPhone;
        this.producerNumberRow = producerNumberRow;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getProducerType() {
        return producerType;
    }

    public void setProducerType(String producerType) {
        this.producerType = producerType;
    }

    public String getProducerAdd() {
        return producerAdd;
    }

    public void setProducerAdd(String producerAdd) {
        this.producerAdd = producerAdd;
    }

    public String getProducerPhone() {
        return producerPhone;
    }

    public void setProducerPhone(String producerPhone) {
        this.producerPhone = producerPhone;
    }

    public int getProducerNumberRow() {
        return producerNumberRow;
    }

    public void setProducerNumberRow(int producerNumberRow) {
        this.producerNumberRow = producerNumberRow;
    }
}
