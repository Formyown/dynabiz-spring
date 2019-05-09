/*
 * Copyright (c) 2018, Deyu Heng. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package test;

import org.dynabiz.mapper.MappingData;
import org.dynabiz.mapper.MappingField;
import org.dynabiz.mapper.ObjectMapper;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapperTest {



    @Test
    public void testMapper() {
        System.out.println("+=========================================================+");
        System.out.println("||              TEST MAPPER BASIC FUNCTION               ||");
        System.out.println("+=========================================================+");
        List<OrderEntity> entities = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Timestamp time = new Timestamp(System.currentTimeMillis());
            OrderEntity entity = new OrderEntity();
            entity.setOrderID(1001 + i);
            entity.setItems(new String[]{"A", "B", "C"});
            entity.setTime(time);
            entity.setTotalDiscount(new BigDecimal("0.5"));
            entity.setTotalPrice(new BigDecimal("100"));
            entity.setPhone("123456789" + i);
            entities.add(entity);
        }

        List<OrderTransfer> orderTransfers = ObjectMapper.mapFromCollection(OrderTransfer.class, entities, (source, target) -> target.setCanReturns(true));

        for (int i = 0; i < 100; i++) {
            OrderEntity entity = entities.get(i);
            OrderTransfer transfer = orderTransfers.get(i);
            System.out.println(entity.toString());
            System.out.println(transfer.toString());
            assert transfer.getOrderID() == entity.getOrderID();
            assert transfer.getItems() == entity.getItems();
            assert transfer.getTime().equals(entity.getTime());
            assert transfer.getTotalDiscount().equals(entity.getTotalDiscount().add(new BigDecimal("10")));
            assert transfer.getTotalPrice().equals(entity.getTotalPrice());
            assert transfer.getPhone().equals(entity.getPhone());
            assert transfer.isCanReturns();
        }

        entities = ObjectMapper.mapFromCollection(OrderEntity.class, orderTransfers);
        for (int i = 0; i < 100; i++) {
            System.out.println(entities.get(i).toString());
        }
    }


    public static class OrderEntity{
        private long orderID;
        private String[] items;
        private Timestamp time;
        private BigDecimal totalDiscount;
        private BigDecimal totalPrice;
        private String phone;

        public long getOrderID() {
            return orderID;
        }

        public void setOrderID(long orderID) {
            this.orderID = orderID;
        }

        public String[] getItems() {
            return items;
        }

        public void setItems(String[] items) {
            this.items = items;
        }

        public Timestamp getTime() {
            return time;
        }

        public void setTime(Timestamp time) {
            this.time = time;
        }

        public BigDecimal getTotalDiscount() {
            return totalDiscount;
        }

        public void setTotalDiscount(BigDecimal totalDiscount) {
            this.totalDiscount = totalDiscount;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String userPhone) {
            this.phone = userPhone;
        }

        @Override
        public String toString() {
            return "OrderEntity{" +
                    "orderID=" + orderID +
                    ", items=" + Arrays.toString(items) +
                    ", time=" + time +
                    ", totalDiscount=" + totalDiscount +
                    ", totalPrice=" + totalPrice +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }

    @MappingData
    public static class OrderTransfer {
        @MappingField
        private long orderID;
        @MappingField
        private String[] items;
        @MappingField
        private Timestamp time;
        @MappingField
        private BigDecimal totalDiscount;
        @MappingField
        private BigDecimal totalPrice;

        @MappingField()
        private String phone;
        private boolean canReturns;

        public long getOrderID() {
            return orderID;
        }

        public void setOrderID(long orderID) {
            this.orderID = orderID;
        }

        public String[] getItems() {
            return items;
        }

        public void setItems(String[] items) {
            this.items = items;
        }

        public Timestamp getTime() {
            return time;
        }

        public void setTime(Timestamp time) {
            this.time = time;
        }

        public BigDecimal getTotalDiscount() {
            return totalDiscount;
        }


        public void setTotalDiscount(BigDecimal totalDiscount) {
            this.totalDiscount = totalDiscount.add(new BigDecimal("10"));
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String userPhone) {
            this.phone = userPhone;
        }

        public boolean isCanReturns() {
            return canReturns;
        }

        public void setCanReturns(boolean canReturns) {
            this.canReturns = canReturns;
        }


        @Override
        public String toString() {
            return "OrderTransfer{" +
                    "orderID=" + orderID +
                    ", items=" + Arrays.toString(items) +
                    ", time=" + time +
                    ", totalDiscount=" + totalDiscount +
                    ", totalPrice=" + totalPrice +
                    ", userPhone='" + phone + '\'' +
                    ", canReturns=" + canReturns +
                    '}';
        }
    }
}
