package nl.codingwithlinda.scribbledash.core.domain.ratings

import nl.codingwithlinda.scribbledash.core.domain.model.Rating

class UnknownRating: Rating{
    override val fromAccuracyPercent: Int
         = -1
    override val toAccuracyPercent: Int
         = -1

}

class Oops(
): Rating{
    override val fromAccuracyPercent: Int
        = 0
    override val toAccuracyPercent: Int
        = 39
}

class Meh(
): Rating{
    override val fromAccuracyPercent: Int
            = 40
    override val toAccuracyPercent: Int
            = 69
}

class Good(
): Rating{
    override val fromAccuracyPercent: Int
            = 70
    override val toAccuracyPercent: Int
            = 79
}

class Great(
): Rating{
    override val fromAccuracyPercent: Int
            = 80
    override val toAccuracyPercent: Int
            = 89
}
class Woohoo(
): Rating{
    override val fromAccuracyPercent: Int
            = 90
    override val toAccuracyPercent: Int
            = 100
}