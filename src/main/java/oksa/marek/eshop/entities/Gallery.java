package oksa.marek.eshop.entities;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.*;
import java.util.List;

@Entity
@RepositoryRestResource(exported = false)
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "image_url")
    private List<String> urls;

    public Gallery() {
    }

    public Gallery(List<String> urls) {
        this.urls = urls;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    @Override
    public String toString() {
        return "Gallery{" +
                "id=" + id +
                ", urls='" + urls + '\'' +
                '}';
    }
}
