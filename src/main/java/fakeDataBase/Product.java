package fakeDataBase;

import lombok.Data;
import java.util.Objects;

/**
 * сущность из фейковой БД
 */
@Data
public class Product {
    private int artId;
    private long barCode;
    private String Name;
    private String Gr20;
    private String Gr21;
    private String Gr22;
    private String Gr23;

    public Product(int artId, long barCode, String name, String gr20, String gr21, String gr22, String gr23) {
        this.artId = artId;
        this.barCode = barCode;
        Name = name;
        Gr20 = gr20;
        Gr21 = gr21;
        Gr22 = gr22;
        Gr23 = gr23;
    }

    @Override
    public String toString() {
        return "Product{" +
                "artId=" + artId +
                ", barCode=" + barCode +
                ", Name='" + Name + '\'' +
                ", Gr20='" + Gr20 + '\'' +
                ", Gr21='" + Gr21 + '\'' +
                ", Gr22='" + Gr22 + '\'' +
                ", Gr23='" + Gr23 + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return artId == product.artId &&
                barCode == product.barCode &&
                Objects.equals(Name, product.Name) &&
                Objects.equals(Gr20, product.Gr20) &&
                Objects.equals(Gr21, product.Gr21) &&
                Objects.equals(Gr22, product.Gr22) &&
                Objects.equals(Gr23, product.Gr23);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artId, barCode, Name, Gr20, Gr21, Gr22, Gr23);
    }
}
