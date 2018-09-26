package amir.ashrafi.ihome.pojo;

import java.util.ArrayList;
import java.util.List;

public class Pagination<T> {
    public List<T> results = new ArrayList<>();
    public int page;
    public int total_pages;
    /*public Metadata metadata;*/
}
