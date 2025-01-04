package com.onkore_backend.onkore.Service.Data;

import com.onkore_backend.onkore.Model.Discount_Code;
import com.onkore_backend.onkore.Repository.AdminRepository;
import com.onkore_backend.onkore.Repository.AvailabilityRepository;
import com.onkore_backend.onkore.Repository.CurrentCourseRepository;
import com.onkore_backend.onkore.Repository.DiscountCodeRepository;
import com.onkore_backend.onkore.Model.Admin;
import com.onkore_backend.onkore.Model.Current_Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteServices {

    @Autowired
    private DiscountCodeRepository discountCodeRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private CurrentCourseRepository currentCourseRepository;

    public String deleteDiscountCode(String codeName, String givenCodePassword, String authCodePassword) {
        if (authCodePassword == null ) {
            if (!authCodePassword.equals(givenCodePassword)) {
                throw new RuntimeException("Invalid auth code");
            }

            throw new RuntimeException("Invalid procedure");
        }

        Optional<Discount_Code> discountCode = discountCodeRepository.findAll().stream()
                .filter(dc -> dc.getCode().equals(codeName))
                .findFirst();
        discountCode.ifPresent(discountCodeRepository::delete);

        return "Discount code deleted successfully";
    }

    public String deleteAdminAvailability(String admin_id, String availability_id) {
        Optional<Admin> admin = adminRepository.findById(admin_id);
        if (admin.isPresent()) {
            Admin foundAdmin = admin.get();
            foundAdmin.getAvailability().removeIf(avail -> avail.getId().equals(availability_id));
            adminRepository.save(foundAdmin);
            availabilityRepository.deleteById(availability_id);

            return "Availability deleted successfully";
        }

        return "Admin or availability id not found";
    }

    public String deleteTopic(String course_id, String topicName) {
        Optional<Current_Course> course = currentCourseRepository.findById(course_id);
        if (course.isPresent()) {
            Current_Course foundCourse = course.get();
            foundCourse.getTopics().removeIf(topic -> topic.equals(topicName));
            currentCourseRepository.save(foundCourse);
            
            return "Topic deleted successfully";
        }

        return "Course not found";
    }

    public void deleteMaterial(String course_id, Integer material_number) {
        // Implementation left empty as per instructions
    }
}
