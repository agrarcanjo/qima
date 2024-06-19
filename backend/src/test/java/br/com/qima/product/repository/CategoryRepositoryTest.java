package br.com.qima.product.repository;

import br.com.qima.product.models.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void testCategoryRepository() {
        String[] electronicsCategoryNames = {"Electronics", "Home Audio", "Karaoke"};
        String[] gamingCategoryNames = {"Gaming", "Console", "Sony"};

        Category karaoke = createCategories(electronicsCategoryNames);
        Category sony = createCategories(gamingCategoryNames);

        findAncestry(karaoke, electronicsCategoryNames);
        findAncestry(sony, gamingCategoryNames);
    }

    Category createCategories(String[] categoryNames) {
        Category parent = null;

        for (String categoryName : categoryNames) {
            Category category = new Category();
            category.setName(categoryName);
            category.setParent(parent);
            parent = categoryRepository.save(category);
        }

        Assert.assertNotNull("category.id", parent.getId());
        return parent;
    }


    void findAncestry(Category category, String[] categoryNames) {
        List<String> ancestry = categoryRepository.findAncestry(category.getId());
        Assert.assertEquals("ancestry.size", categoryNames.length, ancestry.size());

        for (int i = 0; i < categoryNames.length; i++) {
            Assert.assertEquals("ancestor " + i, categoryNames[i], ancestry.get(i));
        }
    }
}
