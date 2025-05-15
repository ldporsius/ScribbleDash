package nl.codingwithlinda.scribbledash.core.domain.draw_examples


interface DrawExampleProvider{
    val examples: List<DrawExample>
    fun getExample(resource: Int): DrawExample

}