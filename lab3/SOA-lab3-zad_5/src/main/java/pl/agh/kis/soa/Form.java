package pl.agh.kis.soa;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

@ManagedBean(name = "form")
@SessionScoped
public class Form {
    private String name;
    private String email;
    private String age;
    private Integer height;
    private String degree;
    private Sex sex = Sex.MALE;

    //him
    private Double chestCircumference;
    private Double hisWaistCircumference;

    //her
    private Double breast;
    private CupSize cupSize;
    private Double herWaistCircumference;
    private Double hips;


    //questions
    private String questionAboutMoney = "do 100zł";
    private String questionAboutBuyingClothesFrequency ="Codziennie";
    private String questionAboutClothesColour = "Kolorowo-jaskrawych";
    private String questionAboutKindOfClothesHer = "garsonki";
    private String questionAboutKindOfClothesHim = "spodnie";

    public void validateHeight(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Integer val = (Integer) value;

        if (sex == Sex.MALE) {
            if (!(val >= 165 && val <= 200)) {
                throw new ValidatorException(new FacesMessage("Height value is invalid!"));
            }
        } else {
            if (!(val >= 150 && val <= 185)) {
                throw new ValidatorException(new FacesMessage("Height value is invalid!"));
            }
        }
    }

    public CupSize[] getCupSizes() {
        return CupSize.values();
    }

    public String getQuestionAboutMoney() {
        return questionAboutMoney;
    }

    public void setQuestionAboutMoney(String questionAboutMoney) {
        this.questionAboutMoney = questionAboutMoney;
    }

    public String getQuestionAboutBuyingClothesFrequency() {
        return questionAboutBuyingClothesFrequency;
    }

    public void setQuestionAboutBuyingClothesFrequency(String questionAboutBuyingClothesFrequency) {
        this.questionAboutBuyingClothesFrequency = questionAboutBuyingClothesFrequency;
    }

    public String getQuestionAboutClothesColour() {
        return questionAboutClothesColour;
    }

    public void setQuestionAboutClothesColour(String questionAboutClothesColour) {
        this.questionAboutClothesColour = questionAboutClothesColour;
    }

    public String getQuestionAboutKindOfClothesHer() {
        return questionAboutKindOfClothesHer;
    }

    public void setQuestionAboutKindOfClothesHer(String questionAboutKindOfClothesHer) {
        this.questionAboutKindOfClothesHer = questionAboutKindOfClothesHer;
    }

    public String getQuestionAboutKindOfClothesHim() {
        return questionAboutKindOfClothesHim;
    }

    public void setQuestionAboutKindOfClothesHim(String questionAboutKindOfClothesHim) {
        this.questionAboutKindOfClothesHim = questionAboutKindOfClothesHim;
    }

    public Double getChestCircumference() {
        return chestCircumference;
    }

    public void setChestCircumference(Double chestCircumference) {
        this.chestCircumference = chestCircumference;
    }

    public Double getHisWaistCircumference() {
        return hisWaistCircumference;
    }

    public void setHisWaistCircumference(Double hisWaistCircumference) {
        this.hisWaistCircumference = hisWaistCircumference;
    }

    public Double getBreast() {
        return breast;
    }

    public void setBreast(Double breast) {
        this.breast = breast;
    }

    public CupSize getCupSize() {
        return cupSize;
    }

    public void setCupSize(CupSize cupSize) {
        this.cupSize = cupSize;
    }

    public Double getHerWaistCircumference() {
        return herWaistCircumference;
    }

    public void setHerWaistCircumference(Double herWaistCircumference) {
        this.herWaistCircumference = herWaistCircumference;
    }

    public Double getHips() {
        return hips;
    }

    public void setHips(Double hips) {
        this.hips = hips;
    }

    public Sex[] getSexValues() {
        return Sex.values();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
