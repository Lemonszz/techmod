package party.lemons.ass.util.registry;

import party.lemons.ass.Ass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoReg
{
	Class type();
	String registry();
	String modid() default Ass.MODID;
	int priority() default 100;
}