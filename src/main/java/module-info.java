module sn.groupeisi.exam_diti4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires static lombok;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires kernel;
    requires layout;

    opens sn.groupeisi.exam_diti4.model to org.hibernate.orm.core, javafx.base;
    opens sn.groupeisi.exam_diti4 to javafx.fxml;
    opens sn.groupeisi.exam_diti4.controller to javafx.fxml;
    exports sn.groupeisi.exam_diti4.controller to javafx.fxml;
    exports sn.groupeisi.exam_diti4;
    exports sn.groupeisi.exam_diti4.model;
}