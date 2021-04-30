<?php

namespace App\Entity;

use App\Repository\CommentRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=CommentRepository::class)
 */
class Comment
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="bigint")
     */
    private $user_id;

    /**
     * @ORM\ManyToOne(targetEntity=Post::class, inversedBy="comments")
     * @ORM\JoinColumn(nullable=false)
     */
    private $post_id;

    /**
     * @ORM\Column(type="datetime")
     */
    private $created_at;

    /**
     * @ORM\Column(type="datetime", nullable=true)
     */
    private $delated_at;

    /**
     * @ORM\Column(type="datetime", nullable=true)
     */
    private $modifier_at;

    /**
     * @ORM\Column(type="string", length=255)
     *
     */
    private $body;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getUserId(): ?string
    {
        return $this->user_id;
    }

    public function setUserId(string $user_id): self
    {
        $this->user_id = $user_id;

        return $this;
    }

    public function getPostId(): ?post
    {
        return $this->post_id;
    }

    public function setPostId(?post $post_id): self
    {
        $this->post_id = $post_id;

        return $this;
    }

    public function getCreatedAt(): ?\DateTimeInterface
    {
        return $this->created_at;
    }

    public function setCreatedAt(\DateTimeInterface $created_at): self
    {
        $this->created_at = $created_at;

        return $this;
    }

    public function getDelatedAt(): ?\DateTimeInterface
    {
        return $this->delated_at;
    }

    public function setDelatedAt(?\DateTimeInterface $delated_at): self
    {
        $this->delated_at = $delated_at;

        return $this;
    }

    public function getModifierAt(): ?\DateTimeInterface
    {
        return $this->modifier_at;
    }

    public function setModifierAt(?\DateTimeInterface $modifier_at): self
    {
        $this->modifier_at = $modifier_at;

        return $this;
    }

    public function getBody(): ?string
    {
        return $this->body;
    }

    public function setBody(string $body): self
    {
        $this->body = $body;

        return $this;
    }
}
