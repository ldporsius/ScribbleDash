package nl.codingwithlinda.scribbledash.core.di

import nl.codingwithlinda.scribbledash.core.domain.draw_examples.DrawExampleProvider

interface AppModule {

    val drawExampleProvider: DrawExampleProvider
}