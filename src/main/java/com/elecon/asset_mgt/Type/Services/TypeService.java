package com.elecon.asset_mgt.Type.Services;

import com.elecon.asset_mgt.Category.Models.CategoryModel;
import com.elecon.asset_mgt.Category.Repository.CategoryRepo;
import com.elecon.asset_mgt.Classification.Models.ClassificationModel;
import com.elecon.asset_mgt.Classification.Repository.ClassificationRepo;
import com.elecon.asset_mgt.Type.payload.CreatetypeDao;
import com.elecon.asset_mgt.Type.Models.TypeModel;
import com.elecon.asset_mgt.Type.Repository.TypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeService {
    @Autowired
    private TypeRepo repo;
    @Autowired
    private ClassificationRepo class_repo;
    @Autowired
    private CategoryRepo category_repo;
    public void save(CreatetypeDao typemodeldao) {
        TypeModel typeModel = new TypeModel();

        typeModel.setStatus(typemodeldao.isStatus());
        typeModel.setType_name(typemodeldao.getType_name());
        typeModel.setType_desc(typemodeldao.getType_desc());

        if (typemodeldao.getCategory_id() != null) {
            CategoryModel category = category_repo.findById(typemodeldao.getCategory_id())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category_id"));
            typeModel.setCategory_id(category);
        }

        if (typemodeldao.getClass_id() != null) {
            ClassificationModel classification = class_repo.findById(typemodeldao.getClass_id())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid class_id"));
            typeModel.setClass_id(classification);
        }
        repo.save(typeModel);
    }

    public List<TypeModel> getAll() {
        return repo.findAll();
    }

    public Optional<TypeModel> findById(Integer id) {
        return repo.findById(id);
    }

    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
