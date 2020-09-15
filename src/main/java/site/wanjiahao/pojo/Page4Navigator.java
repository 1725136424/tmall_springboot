package site.wanjiahao.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

// 分页数据显示 封装了JPA page对象
@Data
@NoArgsConstructor
public class Page4Navigator<T> {

    // 原生JPA page对象
    Page<T> pageFromJPA;

    // 显示分页栏有多少分页
    int navigatePages;

    // 具体显示分页栏
    int[] navigatePageNums;

    int totalPages;

    int number;

    long totalElements;

    int size;

    int numberOfElements;

    List<T> content;

    boolean isHasContent;

    boolean first;

    boolean last;

    boolean isHasNext;

    boolean isHasPrevious;

    // 构造方法
    public Page4Navigator(Page<T> pageFromJPA, int navigatePages) {
        this.pageFromJPA = pageFromJPA;
        this.navigatePages = navigatePages;

        totalPages = pageFromJPA.getTotalPages();

        number  = pageFromJPA.getNumber() + 1;

        totalElements = pageFromJPA.getTotalElements();

        size = pageFromJPA.getSize();

        numberOfElements = pageFromJPA.getNumberOfElements();

        content = pageFromJPA.getContent();

        isHasContent = pageFromJPA.hasContent();

        first = pageFromJPA.isFirst();

        last = pageFromJPA.isLast();

        isHasNext = pageFromJPA.hasNext();

        isHasPrevious  = pageFromJPA.hasPrevious();

        // 初始化分页数据
        initNavigatePageNums();
    }

    public Page4Navigator(Page<T> pageFromJPA) {
        new Page4Navigator<>(pageFromJPA, 5);
    }


    private void initNavigatePageNums() {
        // 总页数
        int totalPages = getTotalPages();
        // 当前页 1 代表第一页
        int number = getNumber();

        if (totalPages <= navigatePages) {
            // 总页数小于分页数，全部显示
            navigatePageNums = new int[totalPages];
            for (int i = 0; i < totalPages; i++) {
                navigatePageNums[i] = i + 1;
            }
        } else {
            /*
            *  可以理下思路
            *       当前情况可以分为三种情况
            *           1. 当前页在分页栏总数一半之内 (这边又可以分为两种情况)
            *               1. 头
            *               2. 尾
            *           2. 当前页在分页栏总数一半之外
            * */
            // 定义目标规则
            int startNumber = number - navigatePages / 2;
            int endNumber = number + navigatePages / 2;
            navigatePageNums = new int[navigatePages];
            if (startNumber < 1) {
                // 头部情况
                startNumber = 1;
            } else if (endNumber > totalPages) {
                // 尾部情况
                startNumber = totalPages - navigatePages + 1;
            }
            /*
            *  内部情况可以不做处理
            *  上面二种情况的处理主要是为了确定起始下标的位置,
            *  而显示的数量已经由navigatePages确定
            * */
            for (int i = 0, j = startNumber; i < navigatePages; i++, j++) {
                navigatePageNums[i] = j;
            }
        }
    }

}
