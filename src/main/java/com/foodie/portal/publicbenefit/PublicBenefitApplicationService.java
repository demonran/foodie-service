package com.foodie.portal.publicbenefit;

import com.foodie.portal.commons.Pagination;
import com.foodie.portal.order.model.Order;
import com.foodie.portal.publicbenefit.command.CreatePublicBenefitCommand;
import com.foodie.portal.publicbenefit.command.UpdatePublicBenefitCommand;
import com.google.common.collect.ImmutableList;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PublicBenefitApplicationService {

    @Autowired
    private PublicBenefitRepository repository;

    public PublicBenefit create(CreatePublicBenefitCommand command) {
        PublicBenefit publicBenefit = PublicBenefit.create(command.getTitle(), command.getImage(), command.getDescription(),
                command.getContent(), command.getTotalFoundation());
        repository.save(publicBenefit);
        return publicBenefit;
    }

    public void extract(Order order) {
        PublicBenefit publicBenefit = repository.findActivated();
        publicBenefit.extract(order);
        repository.save(publicBenefit);
    }


    public Pagination<PublicBenefit> list(int page, int size) {
        return repository.findAll(page - 1, size);
    }

    public PublicBenefit detail(String id) {
        return repository.byId(id);
    }

    public PublicBenefit update(String id, UpdatePublicBenefitCommand command) {
        PublicBenefit publicBenefit = repository.byId(id);
        publicBenefit.update(command.getTitle(), command.getImage(), command.getDescription(),
                command.getContent(), command.getTotalFoundation());
        repository.save(publicBenefit);
        return publicBenefit;
    }

    public void activate(String id) {
        var publicBenefit = repository.byId(id);
        publicBenefit.activate();
        var activatedPublicBenefit = repository.findActivated();
        if(Objects.nonNull(activatedPublicBenefit)) {
            activatedPublicBenefit.pending();
            repository.save(ImmutableList.of(publicBenefit, activatedPublicBenefit));
        }else {
            repository.save(ImmutableList.of(publicBenefit));
        }



    }
}
