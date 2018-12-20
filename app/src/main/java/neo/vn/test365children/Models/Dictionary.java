package neo.vn.test365children.Models;

public class Dictionary  {
    String sNewWord;
    String sTranslate;
    String sImage;
    String sAudio;
    String sDescription;

    public Dictionary(String sNewWord, String sTranslate, String sImage, String sAudio, String sDescription) {
        this.sNewWord = sNewWord;
        this.sTranslate = sTranslate;
        this.sImage = sImage;
        this.sAudio = sAudio;
        this.sDescription = sDescription;
    }

    public String getsNewWord() {
        return sNewWord;
    }

    public void setsNewWord(String sNewWord) {
        this.sNewWord = sNewWord;
    }

    public String getsTranslate() {
        return sTranslate;
    }

    public void setsTranslate(String sTranslate) {
        this.sTranslate = sTranslate;
    }

    public String getsImage() {
        return sImage;
    }

    public void setsImage(String sImage) {
        this.sImage = sImage;
    }

    public String getsAudio() {
        return sAudio;
    }

    public void setsAudio(String sAudio) {
        this.sAudio = sAudio;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }
}
