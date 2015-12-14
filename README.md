Utils
=====================

* [ObjectConverter](#ObjectConverter)
* [Credits](#Credits)


## ObjectConverter
add bindClass(optional) and add bindProperties:

    @BindClass(Pessoa.class)
    public class View {
        @BindProperty("nome")
        private String campo1;
        @BindProperty("seiLa")
        private String campo2;
        @BindProperty("endereco.numero")
        private String campo3;
    }

create class parseTo:

    public class Pessoa {
        private Endereco endereco;
        private String nome;
        private String seiLa;

        public class Endereco {
            private String numero;
            ...
        }
        ...
    }

invoke static method: ObjectConverter.setFields(View) if bind for Class....

    Pessoa pessoa = ObjectConverter.setFields(viewObject);

...or invoke ObjectConverter.setFields(viewObject, pessoaObject)

    Pessoa pessoaObject = new Pessoa();
    ObjectConverter.setFields(viewObject, pessoaObject);

## Credits
[Wiliam Trancoso](https://github.com/wiliamtrancoso "Wiliam Trancoso")
