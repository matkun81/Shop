import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ShopService {

    ShopDao dao = new ShopDao();

    public List<Good> getAll() {

        return dao.findAll();
    }

//    public void addGoods(List<Good> goods) throws IOException {
//        for (Good good : goods) {
//            Good goodInFile = dao.findByName(good.name);
//            if (goodInFile == null) {
//                dao.save(good);
//            } else {
//                dao.deleteByName(good.name);
//                Good newGood = new Good(goodInFile.name, good.count, good.price);
//                dao.save(newGood);
//            }
//        }
//    }

    public void buyGoods(List<Good> goods) throws IOException {
        for (Good good : goods) {
            Good goodInFile = dao.findByName(good.name);
            if (goodInFile == null || goodInFile.count < good.count) {
                throw new IllegalArgumentException("Нету такого количесвта");
            } else {
                Good newGood = new Good(goodInFile.name, goodInFile.count - good.count, good.price);
                dao.deleteByName(good.name);
                dao.save(newGood);
                            }
        }
    }
    public void addGoods(String forAdd) throws IOException {
        try {
            FileWriter fstream = new FileWriter("goods.json");// конструктор с одним параметром - для перезаписи
            BufferedWriter out = new BufferedWriter(fstream); //  создаём буферезированный поток
            out.write(forAdd); // перезаписываем json файл
            out.close(); // закрываем
        } catch (Exception e) {
            System.err.println("Error in file cleaning: " + e.getMessage());
        }
    }
}