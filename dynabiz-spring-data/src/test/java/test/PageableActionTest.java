package test;




import org.dynabiz.spring.data.PageableReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;




@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@PropertySource({"classpath:/application.yaml"})
public class PageableActionTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void initData(){
        System.out.println("Generating testing data..");
        for(int i = 1; i <= 1000; i++){
            Student student = new Student(i, "hello" + i, i % 2 == 0);
            studentRepository.save(student);
        }
    }

    @Test
    public void pageTest() {
        System.out.println("+=========================================================+");
        System.out.println("||                PAGEABLE READER TESTING                ||");
        System.out.println("+=========================================================+");

        PageableReader.read(80,
                (pageable)-> studentRepository.findAll(pageable),
                (pageResult)->{
                    System.out.print(String.format("Progress: %.02f%% : ",
                            ((float)pageResult.getPageable().getPageNumber() + 1)
                                    / (float)pageResult.getTotalPages() * 100f));
                    int index = 1;
                    for(Student s : pageResult){
                        assert s.getStudentID() == pageResult.getPageable().getPageNumber() * 80 + index++;
                    }
                    System.out.println("SUCCESS");
                });

    }

}
