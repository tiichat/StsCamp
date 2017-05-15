package com.tiichat.sts.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tiichat.sts.form.FileUploadForm;
import com.tiichat.sts.form.Form;

/**
 * リクエストモニターは、AOPでリクエスト内容をモニターするクラス。
 *
 * @author tiich
 *
 */
@Aspect
@Component
public class RequestMonitor {

    /**
     * コントローラの全メソッドの内、ModelAndView、Form、BindingResultを引数に持つメソッドをモニターし、
     * フォーム＝画面からの入力情報を、標準出力に出力する。
     * Beforeアノテーションは、AOP対象のメソッドに処理が入る直前に、ポイントカットする指定。
     *
     * args属性を付けることで、引数もウィービングのマッチ条件となる。
     * また、args属性を付けることで、引数をAOP側でも参照できる様になる。
     *
     * @param mav Spring の ModelAndViewオブジェクト。 Beforeのタイミングでは、内容は何もセットされていない状態。
     * @param form 画面からの入力情報を保持しているフォームオブジェクト。 抽象型Formのサブクラスを対象としている。
     * @param result
     */
    @Before("execution(* com.tiichat.sts.controller.*.*(..)) && args(mav,form,result)")
    public void before(ModelAndView mav, Form form, BindingResult result) {
        System.out.println("★★ Request -- " + form.toString());
    }

    /**
     * アップロードファイル情報をモニターする
     *
     * @param fileUploadForm
     */
    @Before("execution(* com.tiichat.sts.controller.*.*(..)) && args(fileUploadForm)")
    public void before2(FileUploadForm fileUploadForm) {
        final MultipartFile mf = fileUploadForm.getFileData();
        System.out.println(
                "★★ Upload -- " + mf.getContentType() + " " + mf.getOriginalFilename()
                        + " " + mf.getSize());
    }

}
