package plants.shop.Shop.models;

import javax.persistence.*;

@Entity
public class plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idS;

    @Column(length = 50)
    private String Title;
    @Column(length = 1000)
    private String description;
    @Column(length = 1000)
    private String photoUrl;
    private String sellerName;
    @Column(length = 20)
    private String price;
    @Column(length = 1000)
    private String contact;
    public void setPhotoUrl() {
        this.photoUrl = "https://vsememy.ru/image/wp-content/uploads/2023/02/1675819069_papik-pro-p-risunok-khishchnogo-rasteniya-12.jpg";
    }

    public plant() {
    }

    public plant(Long idS, String title, String description, String photoUrl, String sellerName, String price, String contact) {
        this.idS = idS;
        Title = title;
        this.description = description;
        this.photoUrl = photoUrl;
        this.sellerName = sellerName;
        this.price = price;
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdS() {
        return idS;
    }

    public void setIdS(Long idS) {
        this.idS = idS;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
