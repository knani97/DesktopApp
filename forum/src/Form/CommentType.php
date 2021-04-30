<?php

namespace App\Form;

use App\Entity\Comment;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class CommentType extends AbstractType
{
  public function buildForm(FormBuilderInterface $builder, array $options)
{
    $builder
            ->add('user_id')
            ->add('created_at')
            ->add('delated_at')
            ->add('modifier_at')
            ->add('body')
            ->add('post_id')
    ;
}

  /*  public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('user_id')
            ->add('created_at')
            ->add('delated_at')
            ->add('modifier_at')
            ->add('body')
            ->add('post_id')
        ;
    }*/

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Comment::class,
        ]);
    }
}
