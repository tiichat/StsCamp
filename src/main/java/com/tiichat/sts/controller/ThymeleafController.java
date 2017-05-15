package com.tiichat.sts.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tiichat.sts.form.EmployeeQueryForm;
import com.tiichat.sts.form.FileUploadForm;
import com.tiichat.sts.service.CsvService;
import com.tiichat.sts.service.DeptService;
import com.tiichat.sts.service.EmployeeService;
import com.tiichat.sts.service.PhotoService;

/**
 * コントローラクラス。
 *
 * Thymeleaf の様なテンプレートエンジンを利用して、HTMLをサーバサイドで合成してレスポンスする場合、
 * アノテーション @Controller を付与する。
 *
 * デフォルトでは、@Controller でアノテートされたクラスのインスタンスは、シングルトンとなる。
 * シングルトンで問題がある場合は、@Scope でコントロール（やサービスなど）のスコープを変更できる。
 *
 * JSONなどをレスポンスするWebAPIの場合は、@RestController でアノテートする。
 *
 * コントローラに、@RequestMapping("/hoge") の様に、@RequestMapping をアノテートした場合、
 * そのコントローラは、http://hogeDomain/hoge に対するリクエストを処理する様になる。
 * ただし、付与するかどうかは任意。
 */
@Controller
@RequestMapping("/mycamp")
public class ThymeleafController {

    /**
     * パス /mycamp に対する、GETリクエストを受け付ける
     *
     * ModelAndView は、モデルとビューを扱う。
     * 似たもので、Model がある。Model は、モデルのみを扱う。
     */
    @RequestMapping(method = GET)
    public ModelAndView showTop(ModelAndView mav) { // テンプレートに情報連携するオブジェクト
        mav.setViewName("/index"); // index.html をビューにセット
        mav.addObject("msg", "input your name :"); // キー"msg"の値をセット
        return mav; // Thymeleaf 処理にフォワード
    }

    /**
     * パス /mycamp に対する、POSTリクエストを受け付ける
     *
     * アノテーション @RequestParam("hoge") は、デフォルトで入力必須が指定される。
     * required=false で、任意項目となるが、 Optional を使った方がスマートに書ける。
     */
    @RequestMapping(method = POST)
    public ModelAndView helloTop(ModelAndView mav,
            @RequestParam("name") String name,
            @RequestParam("age") Optional<Integer> age) {
        mav.setViewName("/index");
        mav.addObject("msg", "Hello " + name + " ! your age " + age.orElse(0));
        mav.addObject("name", name);
        mav.addObject("age", age.orElse(null));
        return mav;
    }

    @Autowired
    private PhotoService photoService;

    /**
     * ファイルアップロード。 アップロードしたファイルは、DBに格納する。
     * 教育用のサンプルなので、拡張子のチェックなどは省いている。（jpeg前提）
     * また、本来ユーザＩＤを指定する想定だが、とりあえず、0固定で設定している。
     *
     * @param fileUploadForm
     * @return
     */
    @RequestMapping(value = "/upload", method = POST)
    public String upload(FileUploadForm fileUploadForm) {
        photoService.save(0, fileUploadForm);
        return "/index";
    }

    /**
     * DBに格納してあるイメージデータを取り出し、レスポンスする。 教育用サンプルなので、jpeg前提。
     * アップロード側で、ユーザＩＤを0固定としているため、こちらも同じにしておく。
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/{name}.jpg", method = GET, produces = "image/jpeg")
    @ResponseBody
    public byte[] jpeg(@PathVariable String name) {
        return photoService.load(0).getImage();
    }

    /*
     * CsvService のインプリを、DI している。
     *
     * アノテーション @Autowired で DIしたオブジェクトは、デフォルトでシングルトンになることを忘れないこと。
     * CsvServiceインタフェースの実装クラスで、インスタンス変数を使っている場合など、
     * シングルトンでは駄目な場合がある。
     *
     * 例えば、セッションスコープのサービスを DI したいとする。
     * このとき、コントローラのスコープがシングルトンの場合、DIするサービスよりもスコープが長い為、
     * 当然ながら、エラーとなる。
     * これを回避するためには、サービス側のスコープに、proxyMode = ScopedProxyMode.TARGET_CLASS
     * を設定するなどの必要がある。
     */
    @Autowired
    private CsvService csvService;

    /**
     * パスの一部をパラメタとして取りたい場合、{変数名} とし、@PathVariable で引数を定義する。
     *
     * アノテーション @RequestMapping の produces で、Content-Type を設定。
     * "text/csv" で、CSVファイルをダウンロードする様になる。
     * org.springframework.http.MediaType に定数が定義してあるので、基本はそっちを使う。
     *
     * アノテーション @ResponseBody を付けると、戻り値で直接レスポンスのコンテンツを返すことができる。
     * 勿論、テンプレートエンジンを使って返しても良い。 Thymeleafは、HTML以外にも色々なフォーマットに対応している。
     *
     * ダウンロードファイル名は、パスの名前。/mycamp/hoge.csv なら、hoge.csv でダウンロード。
     */
    @RequestMapping(value = "/{name}.csv", method = GET, produces = "text/csv")
    @ResponseBody
    public String csvDownload(@PathVariable String name) {
        return csvService.get(name);
    }

    /*
     * Deptサービスを DI。
     */
    @Autowired
    private DeptService deptService;

    /*
     * Employeeサービスを DI。
     */
    @Autowired
    private EmployeeService employeeService;

    /**
     * パス /mycamp/chapter14/listEmployee の、GET と POST を受け付ける。
     *
     * アノテーション @ModelAttribute("hoge") で、ModelAndView に、"hoge"
     * という名前でフォームオブジェクトが追加される。
     * ModelAndViewオブジェクトは、Thymeleafに渡っていくので、
     * Thymeleaf側で、<form th:object="${hoge}>...
     * といった感じで参照することができる。
     * ただし、@ModelAttribute("hoge")は省略できる。
     * その場合、フォームクラスの先頭を大文字⇒小文字にしたフォーム名で処理をしようとする。
     *
     * アノテーション @Validated をフォームオブジェクトに対して付与することで、バリデーションチェックがかかる。
     * バリデーションチェック内容は、フォームクラス（下例だと、EmployeeQueryForm）に記述する。
     * フォームクラスには、ゲッタ・セッタが必須なので注意。
     *
     * BindingResult には、バリデーションチェック結果が格納される。
     * 下例のコードでは、入力項目にエラーがあった場合でも、社員情報を検索しに行き、一覧が表示されてしまう。
     * result.hasErrors()を使えば、エラー時に社員一覧を検索しに行かない様に制御することができる。
     */
    @RequestMapping(value = "/chapter14/listEmployee", method = { GET, POST })
    public ModelAndView employeeList(ModelAndView mav,
            @ModelAttribute("form") @Validated EmployeeQueryForm form,
            BindingResult result) {
        mav.setViewName("/chapter14/listEmployee");
        mav.addObject("depts", deptService.getList());
        mav.addObject("employee", employeeService.getList(form));
        return mav;
    }

}
